package com.offerup.postitem;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.offerup.postitem.api.service.ItemResponse;
import com.offerup.postitem.api.service.ItemService;
import com.offerup.postitem.api.service.PhotosService;
import com.offerup.postitem.api.service.PhotosUploadService;
import com.offerup.postitem.api.service.S3Photo;
import com.offerup.postitem.api.service.UserResponse;
import com.offerup.postitem.api.service.UserService;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import retrofit.RequestInterceptor;
import retrofit.ResponseCallback;
import retrofit.RestAdapter;
import retrofit.Callback;
//import retrofit.RxJavaCallAdapterFactory;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private RestAdapter radapter;
    private String token = null;
    private String user_agent = null;
    private String device_id = null;
    SharedPreferences pref;

    private String strEmail = null;
    private String strPwd = null;

    private Subscription subscription;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_layout);

        if (shouldAskPermissions()) {
            askPermissions();
        }

        device_id = Settings.Secure.getString(getContentResolver(), "android_id");

        user_agent = "OfferUp/2.5.2 (build: 140111209; ";
        user_agent = user_agent + Build.MANUFACTURER + " " + Build.MODEL + " " + Build.ID + "; Android " + Build.VERSION.RELEASE + "; " + Locale.getDefault().toString() + ")";

        Gson gson = new GsonBuilder().create();

        radapter = new RestAdapter.Builder()
                .setEndpoint("https://offerupnow.com/api/v2")
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RestAdapterInterceptor(user_agent, device_id, token))
                .build();

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        pref = getApplicationContext().getSharedPreferences("Post", MODE_PRIVATE);
        strEmail = pref.getString("email", null);
        strPwd = pref.getString("pwd", null);
        email.setText(strEmail);
        password.setText(strPwd);

        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        /*
        Intent service = new Intent(this.getApplicationContext(), ServerStatusService.class);
        getApplicationContext().startService(service);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        title = (EditText) findViewById(R.id.title);
        descript = (EditText) findViewById(R.id.descript);
        image = (EditText) findViewById(R.id.img_path);
        zipcode = (EditText) findViewById(R.id.zipcode);

        Button post = (Button)findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProduct();
            }
        });*/
    }

    private void login()
    {
        UserService user = radapter.create(UserService.class);

        strEmail = email.getText().toString();
        strPwd = password.getText().toString();

        Observable<UserResponse> user_response = user.loginWithEmail(strEmail, strPwd);
        subscription = user_response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("email", strEmail);
                        editor.putString("pwd", strPwd);
                        editor.commit();
                        Toast.makeText(MainActivity.this, "Succeses", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        token = userResponse.getToken();
                        Intent intent = new Intent(MainActivity.this, PostActivity.class);
                        intent.putExtra("verify_token", token);
                        startActivityForResult(intent, 1);
                        finish();
                        //Toast.makeText(MainActivity.this, "Succeses : " + userResponse.getToken(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Uri getItemPhotoTempDirNewFilePathUri(int id)
    {
        File dir = new File(new File("/data/data/com.offerup/files/", "images"), String.valueOf(id));
        dir.mkdirs();
        return Uri.fromFile(new File(dir, String.format("%s.jpg", new Object[]{UUID.randomUUID()})));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
