package com.offerup.postitem;

import android.net.Uri;

public class ItemPostPhoto
{
    private Uri imageUri;
    private int mediaId;
    private String uuid;

    public ItemPostPhoto(String paramString, Uri paramUri)
    {
        this.uuid = paramString;
        this.imageUri = paramUri;
        this.mediaId = -1;
    }
}
