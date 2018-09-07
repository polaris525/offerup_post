package com.offerup.postitem.api.service;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

import hirondelle.date4j.DateTime;

public abstract interface UserService
{
  @GET("/user/me/")
  public abstract Observable<UserResponse> loginWithEmail(@Query("username") String paramString1, @Query("password") String paramString2);
}
