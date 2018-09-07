package com.offerup.postitem.api.service;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.http.Body;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public abstract interface PhotosUploadService
{
  @PUT("/uploads/{uuid}.jpg")
  public abstract void upload(@Path("uuid") String paramString1, @Query(encodeValue=false, value="Signature") String paramString2, @Query(encodeValue=false, value="Expires") String paramString3, @Query(encodeValue=false, value="AWSAccessKeyId") String paramString4, @Query(encodeValue=false, value="x-amz-acl") String paramString5, @Query(encodeValue=false, value="x-amz-security-token") String paramString6, @Body TypedFile paramTypedFile, Callback<ResponseCallback> paramCallback);
}
