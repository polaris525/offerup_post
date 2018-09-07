package com.offerup.postitem;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public abstract interface SystemService
{
  @GET("/a.offerupnow.com/status-android.json")
  public abstract void getStatusJson(@Query("fauid") String paramString1, @Query("d") String paramString2, @Query("apid") String paramString3, @Query("gaid") String paramString4, Callback<SystemStatus> paramCallback);
}
