package com.lymin.nexonlinemarket.model;

import android.net.Uri;

public class PhotoUpload {
    private int id;
    private Uri uri;

    public PhotoUpload(int id, Uri uri) {
        this.id = id;
        this.uri = uri;
    }

    public PhotoUpload() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
