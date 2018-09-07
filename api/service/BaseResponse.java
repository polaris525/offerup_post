package com.offerup.postitem.api.service;

import hirondelle.date4j.DateTime;

public class BaseResponse
{
  public class Error
  {
    String debugMessage;
    String description;
    String errorCode;
    String helpUrl;
    String title;

    public String getDebugMessage()
    {
      return this.debugMessage;
    }

    public String getDescription()
    {
      return this.description;
    }

    public String getErrorCode()
    {
      return this.errorCode;
    }

    public String getHelpUrl()
    {
      return this.helpUrl;
    }

    public String getTitle()
    {
      return this.title;
    }
  }

  public class Status
  {
    String code;
    Error error;
    String message;
    DateTime serverTime;
    String status;

    public String getCode()
    {
      return this.code;
    }

    public Error getError()
    {
      return this.error;
    }

    public String getMessage()
    {
      return this.message;
    }

    public DateTime getServerTime()
    {
      return this.serverTime;
    }

    public String getStatus()
    {
      return this.status;
    }
  }

  boolean authenticationError;
  Status status;

  public Status getStatus()
  {
    if (this.status == null) {
      this.status = new Status();
    }
    return this.status;
  }

  public boolean isAuthenticationError()
  {
    return this.authenticationError;
  }

  public boolean isSuccess()
  {
    return (this.status != null) && ("ok".equalsIgnoreCase(this.status.getStatus()));
  }

  public void setAuthenticationError(boolean paramBoolean)
  {
    this.authenticationError = paramBoolean;
  }
}
