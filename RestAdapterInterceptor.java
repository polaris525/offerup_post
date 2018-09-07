package com.offerup.postitem;

import android.provider.Settings;

import retrofit.RequestInterceptor;

public class RestAdapterInterceptor implements RequestInterceptor {
    String token = null;
    String device = null;
    String ua_str = null;
    RestAdapterInterceptor(String user_agent, String d_token, String str_token)
    {
        ua_str = user_agent;
        device = d_token;
        token = str_token;
    }

    @Override
    public void intercept(RequestFacade req) {
        if (ua_str != null)
            req.addHeader("User-Agent", ua_str);
        if (device != null)
            req.addHeader("X-OU-D-TOKEN", device);
        if (token != null) {
            req.addHeader("X-OU-AUTH-TOKEN", token);
        }
    }
}
