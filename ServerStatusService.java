package com.offerup.postitem;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class ServerStatusService
        extends IntentService
{
    public ServerStatusService()
    {
        super("ServerStatusService");
    }

    protected void onHandleIntent(Intent paramIntent)
    {
        /*
        try
        {
            OkHttpClient http_client = new OkHttpClient();
            RestAdapter s3_adapter = new RestAdapter.Builder()
                    .setExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor())
                    .setClient(new OkClient(new OkHttpClient()))
                    .setEndpoint("https://s3.amazonaws.com")
                    .setRequestInterceptor(new RestAdapterInterceptor(null))
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .build();
            SystemService service = s3_adapter.create(SystemService.class);
            service.getStatusJson("0",
                    "80ce62ece8c54025", "9787e1e9-f275-4475-b9ca-5750dd0073bf", null, new Callback<SystemStatus>() {
                        @Override
                        public void success(SystemStatus systemStatus, Response response) {
                            Toast.makeText(getApplicationContext(), "Server Status Service -> Succeses : " + systemStatus.systemMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getApplicationContext(), "Server Status Service -> Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e)
        {
        }*/
    }
}
