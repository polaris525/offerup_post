package com.offerup.postitem.api.service;

import hirondelle.date4j.DateTime;

public class User
{
  boolean acknowledgedPayments;
  boolean addedCard;
  String avatarExtraLarge;
  String avatarLarge;
  String avatarSquare;
  boolean cardOnFile;
  DateTime dateJoined;
  String email;
  String facebookId;
  DateTime firstItemPosted;
  String firstName;
  DateTime firstPayoutMade;
  DateTime firstPayoutReceived;
  int id;
  boolean isPhoneNumberVerified;
  boolean itemsArchived;
  boolean itemsAutoArchived;
  String lastName;
  boolean newsletter;
  String paymentCommissionRate;
  boolean paymentEnabled;
  String publicLocationName;
  boolean sellerPaymentEnabled;
  boolean termsAccepted;
  String token;
  long userId;
  boolean usesDefaultAvatar;
  int verificationStatus;
  boolean verifiedUser;

  public String getAvatarExtraLarge()
  {
    return this.avatarExtraLarge;
  }

  public String getAvatarLarge()
  {
    return this.avatarLarge;
  }

  public String getAvatarSquare()
  {
    return this.avatarSquare;
  }

  public DateTime getDateJoined()
  {
    return this.dateJoined;
  }

  public String getEmail()
  {
    return this.email;
  }

  public String getFacebookId()
  {
    return this.facebookId;
  }

  public DateTime getFirstItemPosted()
  {
    return this.firstItemPosted;
  }

  public String getFirstName()
  {
    return this.firstName;
  }

  public DateTime getFirstPayoutMade()
  {
    return this.firstPayoutMade;
  }

  public DateTime getFirstPayoutReceived()
  {
    return this.firstPayoutReceived;
  }

  public int getId()
  {
    return this.id;
  }

  public String getLastName()
  {
    return this.lastName;
  }

  public String getPaymentCommissionRate()
  {
    return this.paymentCommissionRate;
  }

  public String getPublicLocationName()
  {
    return this.publicLocationName;
  }

  public String getToken()
  {
    return this.token;
  }

  public long getUserId()
  {
    return this.userId;
  }

  public int getVerificationStatus()
  {
    return this.verificationStatus;
  }

  public boolean hasAcknowledgedPayments()
  {
    return this.acknowledgedPayments;
  }

  public boolean hasAddedCard()
  {
    return this.addedCard;
  }

  public boolean hasNewsletter()
  {
    return this.newsletter;
  }

  public boolean isCardOnFile()
  {
    return this.cardOnFile;
  }

  public boolean isItemsArchived()
  {
    return this.itemsArchived;
  }

  public boolean isItemsAutoArchived()
  {
    return this.itemsAutoArchived;
  }

  public boolean isPaymentEnabled()
  {
    return this.paymentEnabled;
  }

  public boolean isPhoneNumberVerified()
  {
    return this.isPhoneNumberVerified;
  }

  public boolean isSellerPaymentEnabled()
  {
    return this.sellerPaymentEnabled;
  }

  public boolean isTermsAccepted()
  {
    return this.termsAccepted;
  }

  public boolean isUsingDefaultAvatar()
  {
    return this.usesDefaultAvatar;
  }

  public boolean isVerifiedUser()
  {
    return this.verifiedUser;
  }

  public void setAcknowledgedPayments(boolean paramBoolean)
  {
    this.acknowledgedPayments = paramBoolean;
  }

  public void setAddedCard(boolean paramBoolean)
  {
    this.addedCard = paramBoolean;
  }

  public void setAvatarLarge(String paramString)
  {
    this.avatarLarge = paramString;
  }

  public void setAvatarSquare(String paramString)
  {
    this.avatarSquare = paramString;
  }

  public void setCardOnFile(boolean paramBoolean)
  {
    this.cardOnFile = paramBoolean;
  }

  public void setDateJoined(DateTime paramDateTime)
  {
    this.dateJoined = paramDateTime;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public void setFacebookId(String paramString)
  {
    this.facebookId = paramString;
  }

  public void setFirstItemPosted(DateTime paramDateTime)
  {
    this.firstItemPosted = paramDateTime;
  }

  public void setFirstName(String paramString)
  {
    this.firstName = paramString;
  }

  public void setFirstPayoutMade(DateTime paramDateTime)
  {
    this.firstPayoutMade = paramDateTime;
  }

  public void setFirstPayoutReceived(DateTime paramDateTime)
  {
    this.firstPayoutReceived = paramDateTime;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setItemsArchived(boolean paramBoolean)
  {
    this.itemsArchived = paramBoolean;
  }

  public void setItemsAutoArchived(boolean paramBoolean)
  {
    this.itemsAutoArchived = paramBoolean;
  }

  public void setLastName(String paramString)
  {
    this.lastName = paramString;
  }

  public void setNewsletterState(boolean paramBoolean)
  {
    this.newsletter = paramBoolean;
  }

  public void setPaymentCommissionRate(String paramString)
  {
    this.paymentCommissionRate = paramString;
  }

  public void setPhoneNumberVerified(boolean paramBoolean)
  {
    this.isPhoneNumberVerified = paramBoolean;
  }

  public void setPublicLocationName(String paramString)
  {
    this.publicLocationName = paramString;
  }

  public void setSellerPaymentEnabled(boolean paramBoolean)
  {
    this.sellerPaymentEnabled = paramBoolean;
  }

  public void setTermsAccepted(boolean paramBoolean)
  {
    this.termsAccepted = paramBoolean;
  }

  public void setToken(String paramString)
  {
    this.token = paramString;
  }

  public void setUserId(long paramLong)
  {
    this.userId = paramLong;
  }

  public void setUsingDefaultAvatar(boolean paramBoolean)
  {
    this.usesDefaultAvatar = paramBoolean;
  }

  public void setVerificationStatus(int paramInt)
  {
    this.verificationStatus = paramInt;
  }

  public void setVerifiedUser(boolean paramBoolean)
  {
    this.verifiedUser = paramBoolean;
  }
}
