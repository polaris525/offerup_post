package com.offerup.postitem.api.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class S3Photo
{
  private String upload_url = null;
  private String uuid = null;
  
  @Nullable
  public String getUploadUrl()
  {
    return this.upload_url;
  }
  
  @NonNull
  public String getUuid()
  {
    return this.uuid;
  }
}
