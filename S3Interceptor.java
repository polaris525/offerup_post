package com.offerup.postitem;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;

import java.io.IOException;

class S3Interceptor implements Interceptor
{
  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
      Response response = chain.proceed(chain.request().newBuilder().removeHeader("Content-Type").build());

      return response;
  }
}
