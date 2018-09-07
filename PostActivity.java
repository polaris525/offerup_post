package com.offerup.postitem;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.offerup.postitem.api.service.ItemResponse;
import com.offerup.postitem.api.service.ItemService;
import com.offerup.postitem.api.service.PhotosService;
import com.offerup.postitem.api.service.PhotosUploadService;
import com.offerup.postitem.api.service.S3Photo;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RestAdapter;
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
import rx.schedulers.Schedulers;

public class PostActivity extends AppCompatActivity {

    Spinner category_list;
    ArrayAdapter<String> adapter;
    String[] str_list = {"Antiques", "Appliances", "Arts & Crafts", "Audio Equipment", "Auto Parts",
            "Baby & Kids", "Beauty & Health", "Bicycles", "Boats & Marine", "Books & Magazines",
            "Business Equipment", "Campers & RVs", "Cars & Trucks", "CDs & DVDs", "Cell Phones",
            "Clothing & Shoes", "Collectibles", "Computer Equipment", "Computer Software", "Electronics",
            "Farming", "Furniture", "Games & Toys", "General", "Home & Garden",
            "Household", "Jewelry & Accessories", "Motorcycles", "Musical instruments", "Pet supplies",
            "Photography", "Sports & outdoors", "Tickets", "Tools & Machinery", "TVs",
            "Video equipment", "Video games"};

    int[] id_list = {6, 7, 8, 9, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
            0x11, 0x12, 0x13, 0x0A, 0x14, 5, 0x15, 0x16, 0x17, 1,
            0x18, 4, 0x19, 3, 0x1A, 2, 0x1B, 0x1D, 0x1E, 0x27,
            0x20, 0x1F, 0x21, 0x22, 0x26, 0x25, 0x23};

    private Subscription subscription;
    private EditText title;
    private EditText descript;
    private EditText price;
    private EditText img_zip_list;
    private String token = null;
    private String user_agent = null;
    private String device_id = null;
    private int nCategory = 1;

    private S3Photo[] m_s3Photo = null;
    private RestAdapter myAdapter = null;
    private Vector<String> m_vecImage = null;
    private Vector<String> m_vecZip = null;

    private static int nIndex = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        device_id = Settings.Secure.getString(getContentResolver(), "android_id");

        user_agent = "OfferUp/2.5.2 (build: 140111209; ";
        user_agent = user_agent + Build.MANUFACTURER + " " + Build.MODEL + " " + Build.ID + "; Android " + Build.VERSION.RELEASE + "; " + Locale.getDefault().toString() + ")";

        Bundle bundle_token = getIntent().getExtras();
        token = bundle_token.getString("verify_token");

        category_list = (Spinner) findViewById(R.id.categorylist);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str_list);
        category_list.setAdapter(adapter);

        category_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nCategory = id_list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                nCategory = id_list[0];
            }
        });

        img_zip_list = (EditText)findViewById(R.id.itemlist);
        title = (EditText)findViewById(R.id.title);
        descript = (EditText)findViewById(R.id.description);
        price = (EditText)findViewById(R.id.price);


        Button post = (Button)findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nIndex = 0;
                if (m_vecImage != null)
                    m_vecImage.clear();
                else
                    m_vecImage = new Vector<String>();

                if (m_vecZip != null)
                    m_vecZip.clear();
                else
                    m_vecZip = new Vector<String>();
                GetList();

                postbyindex(nIndex);
            }
        });
    }

    public void postbyindex(int index)
    {
        postProduct(title.getText().toString(), descript.getText().toString(), Double.valueOf(price.getText().toString()), nCategory, m_vecImage.get(index), m_vecZip.get(index));
    }

    public void GetList()
    {
        String path = img_zip_list.getText().toString();

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(new File(path)));
            do {
                    StringReader sr;
                    String strLine = dis.readLine();
                    if(strLine==null) {
                        break;
                    }
                    String[] strComma = strLine.split(",");
                    int index = path.lastIndexOf('/');
                    String imagePath = path.substring(0, index) + '/' + strComma[0];

                    m_vecImage.add(imagePath);
                    m_vecZip.add((strComma[1]));
            }
            while(true);

            try {
                dis.close();
            } catch (IOException e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getS3Parameter(String paramString1, String paramString2)
    {
        paramString1 = paramString1 + "=";
        int i = paramString2.indexOf(paramString1);
        if (i >= 0)
        {
            int k = i + paramString1.length();
            int j = paramString2.indexOf('&', k);
            i = j;
            if (j < 0) {
                i = paramString2.length();
            }
            return paramString2.substring(k, i);
        }
        return null;
    }



    private void postProduct(final String strTitle, final String strDescript, final Double dblPrice, final int nCategory, final String image, final String zip)
    {
        if (token != null)
        {
            Gson gson = new GsonBuilder().create();

            myAdapter = new RestAdapter.Builder()
                    .setExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor())
                    .setClient(new OkClient(new OkHttpClient()))
                    .setEndpoint("https://offerupnow.com/api/v2")
                    .setConverter(new GsonConverter(gson))
                    .setRequestInterceptor(new RestAdapterInterceptor(user_agent, device_id, token))
                    .build();

            PhotosService photo = myAdapter.create(PhotosService.class);

            photo.generateUrls(1, new Callback<S3Photo[]>() {
                @Override
                public void success(S3Photo[] s3Photos, Response response) {
                    m_s3Photo = s3Photos;
                    //Toast.makeText(PostActivity.this, "GenerateUrls -> Succeses : " + s3Photos[0].getUuid(), Toast.LENGTH_SHORT).show();
                    if (m_s3Photo != null)
                    {
                        String uploadUrl = m_s3Photo[0].getUploadUrl();
                        if (uploadUrl != null)
                        {
                            String s1 = getS3Parameter("x-amz-security-token", uploadUrl);
                            String s2 = getS3Parameter("Signature", uploadUrl);
                            String s3 = getS3Parameter("x-amz-acl", uploadUrl);
                            String s4 = getS3Parameter("Expires", uploadUrl);
                            String s5 = getS3Parameter("AWSAccessKeyId", uploadUrl);

                            Uri upload_uri = Uri.parse(uploadUrl);
                            String home_url = String.format("%s://%s", upload_uri.getScheme(), upload_uri.getHost());

                            File temp = new File(image);
                            if (temp == null) {
                                Toast.makeText(PostActivity.this, "GenerateUrls -> Image File doesn't exist.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            TypedFile image_file = new TypedFile("image/jpg", new File(image));

                            RestAdapter s3_upload_adapter = null;
                            try
                            {
                                SSLContext localSSLContext = SSLContext.getInstance("TLS");
                                localSSLContext.init(null, null, null);

                                OkHttpClient http_client = new OkHttpClient();
                                http_client.setSslSocketFactory(localSSLContext.getSocketFactory());
                                http_client.setConnectTimeout(30, TimeUnit.SECONDS);
                                http_client.networkInterceptors().add(new S3Interceptor());

                                s3_upload_adapter = new RestAdapter.Builder()
                                        .setExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor())
                                        .setClient(new OkClient(http_client))
                                        .setEndpoint(home_url)
                                        .setRequestInterceptor(new RestAdapterInterceptor(user_agent, device_id, null))
                                        .build();
                            }
                            catch (GeneralSecurityException paramExecutorService)
                            {
                                throw new AssertionError();
                            }

                            PhotosUploadService upload_service = s3_upload_adapter.create(PhotosUploadService.class);

                            upload_service.upload(m_s3Photo[0].getUuid(), s2, s4, s5, s3, s1, image_file, new Callback<ResponseCallback>() {
                                @Override
                                public void success(ResponseCallback responseCallback, Response response) {
                                    Toast.makeText(PostActivity.this, "Upload -> Succeses" + response.getHeaders(), Toast.LENGTH_SHORT).show();

//                                    Gson gson = new GsonBuilder().create();
//
//                                    myAdapter = new RestAdapter.Builder()
//                                            .setExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor())
//                                            .setClient(new OkClient(new OkHttpClient()))
//                                            .setEndpoint("https://offerupnow.com/api/v2")
//                                            .setConverter(new GsonConverter(gson))
//                                            .setRequestInterceptor(new RestAdapterInterceptor(user_agent, device_id, null))
//                                            .build();

                                    ItemService service = myAdapter.create(ItemService.class);
                                    Observable<ItemResponse> test = service.createItem(strTitle,
                                            0x28, nCategory, strDescript, dblPrice,
                                            2, m_s3Photo[0].getUuid(),
                                            0, zip, 0.0D, 0.0D, 0,null);

                                    subscription = test.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<ItemResponse>() {
                                                @Override
                                                public void onCompleted() {
                                                    Toast.makeText(PostActivity.this, "Post -> Succeses", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    Toast.makeText(PostActivity.this, "Post -> Error : " + e.toString() + " Index = " + nIndex, Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onNext(ItemResponse itemResponse) {
                                                    nIndex++;
                                                    if (nIndex < m_vecImage.size()) {
                                                        try {
                                                            Thread.sleep(10000);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        postbyindex(nIndex);
                                                    }
                                                    Toast.makeText(PostActivity.this, "Post -> Succeses : " + nIndex, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(PostActivity.this, "Upload -> Failure" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(PostActivity.this, "Generate Urls -> Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}