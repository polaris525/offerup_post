package com.offerup.postitem.api.service;

import android.content.ClipData;

import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import rx.Observable;

import hirondelle.date4j.DateTime;

public abstract interface ItemService
{
  @Multipart
  @POST("/items/")
  public abstract Observable<ItemResponse> createItem(@Part("title") String paramString1,
                                                    @Part("condition") Integer paramInteger1,
                                                    @Part("category") Integer paramInteger2,
                                                    @Part("description") String paramString2,
                                                    @Part("price") Double paramDouble1,
                                                    @Part("listing_type") Integer paramInteger3,
                                                    @Part("photos") String paramString3,
                                                    @Part("state") Integer paramInteger4,
                                                    @Part("location_zipcode") String paramString4,
                                                    @Part("latitude") Double paramDouble2,
                                                    @Part("longitude") Double paramDouble3,
                                                    @Part("share_facebook") int paramInt,
                                                    @Part("fb_access_token") String paramString5);

  /*
  @DELETE("/item/{itemId}/")
  public abstract void deleteItem(@Path("itemId") long paramLong, Callback<DeleteItemResponse> paramCallback);
  
  @GET("/item/{itemId}/")
  public abstract Observable<ItemResponse> getItem(@Path("itemId") long paramLong);
  
  @GET("/item/{itemId}/")
  public abstract void getItem(@Path("itemId") long paramLong, Callback<ItemResponse> paramCallback);
  
  @GET("/item/{itemId}/rate-transaction/")
  public abstract void getItemWithUserEngagement(@Path("itemId") long paramLong, Callback<RateTransactionResponse> paramCallback);
  
  @PUT("/item/{itemId}/sold/?in_app_ratings=true")
  public abstract void markItemAsSold(@Path("itemId") long paramLong, Callback<ItemResponse> paramCallback);
  
  @Multipart
  @PUT("/item/{itemId}/")
  public abstract Observable<ItemResponse> updateItem(@Path("itemId") long paramLong, @Part("title") String paramString1, @Part("condition") Integer paramInteger1, @Part("category") Integer paramInteger2, @Part("description") String paramString2, @Part("price") Double paramDouble1, @Part("listing_type") Integer paramInteger3, @Part("photos") String paramString3, @Part("state") Integer paramInteger4, @Part("location_zipcode") String paramString4, @Part("latitude") Double paramDouble2, @Part("longitude") Double paramDouble3);
  
  @FormUrlEncoded
  @PUT("/item/{itemId}/")
  public abstract void updateItem(@Path("itemId") long paramLong, @Field("state") int paramInt, Callback<ItemResponse> paramCallback);
  */
}
