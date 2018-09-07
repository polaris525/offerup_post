package com.offerup.postitem.api.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public abstract interface PhotosService
{
  @GET("/photos/generate_urls/")
  public abstract void generateUrls(@Query("number_of_urls") Integer paramInteger, Callback<S3Photo[]> paramCallback);
}
