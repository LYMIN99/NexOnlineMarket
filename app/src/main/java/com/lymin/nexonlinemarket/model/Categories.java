package com.lymin.nexonlinemarket.model;

import android.graphics.drawable.Drawable;

public class Categories {

    public int image;
    public Drawable imageDrw;
    public int categoryID;
    public String title;

    public Categories(int image, Drawable imageDrw, int categoryID, String title) {
        this.image = image;
        this.imageDrw = imageDrw;
        this.categoryID = categoryID;
        this.title = title;
    }

    public Categories() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Drawable getImageDrw() {
        return imageDrw;
    }

    public void setImageDrw(Drawable imageDrw) {
        this.imageDrw = imageDrw;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
