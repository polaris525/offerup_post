package com.offerup.postitem;

import hirondelle.date4j.DateTime;

public class SystemStatus
{
  int appLatestBuildGingerbread;
  int appLatestBuildIceCreamSandwich;
  int appMinBuildGingerbread;
  int appMinBuildIceCreamSandwich;
  DateTime appSoftExpDateGingerbread;
  DateTime appSoftExpDateIceCreamSandwich;
  int appSoftMinBuildGingerbread;
  int appSoftMinBuildIceCreamSandwich;
  String googleMapsKey;
  String noSearchResultsText;
  int reserveMinBuild;
  String systemMessage;
  String systemStatus;
  
  public int getAppLatestBuildGingerbread()
  {
    return this.appLatestBuildGingerbread;
  }
  
  public int getAppLatestBuildIceCreamSandwich()
  {
    return this.appLatestBuildIceCreamSandwich;
  }
  
  public int getAppMinBuildGingerbread()
  {
    return this.appMinBuildGingerbread;
  }
  
  public int getAppMinBuildIceCreamSandwich()
  {
    return this.appMinBuildIceCreamSandwich;
  }
  
  public DateTime getAppSoftExpDateGingerbread()
  {
    return this.appSoftExpDateGingerbread;
  }
  
  public DateTime getAppSoftExpDateIceCreamSandwich()
  {
    return this.appSoftExpDateIceCreamSandwich;
  }
  
  public int getAppSoftMinBuildGingerbread()
  {
    return this.appSoftMinBuildGingerbread;
  }
  
  public int getAppSoftMinBuildIceCreamSandwich()
  {
    return this.appSoftMinBuildIceCreamSandwich;
  }
  
  public String getGoogleMapsKey()
  {
    return this.googleMapsKey;
  }
  
  public String getNoSearchResultsText()
  {
    return this.noSearchResultsText;
  }
  
  public int getReserveMinBuild()
  {
    return this.reserveMinBuild;
  }
  
  public String getSystemMessage()
  {
    return this.systemMessage;
  }
  
  public String getSystemStatus()
  {
    return this.systemStatus;
  }
}
