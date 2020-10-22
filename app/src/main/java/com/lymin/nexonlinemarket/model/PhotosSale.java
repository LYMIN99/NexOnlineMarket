package com.lymin.nexonlinemarket.model;

public class PhotosSale {
    private String imageUrl;

    public PhotosSale() {
    }

    public PhotosSale(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
