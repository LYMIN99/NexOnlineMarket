package com.lymin.nexonlinemarket.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

public class WishListItem extends RealmObject {

    private String id;
    private String sellerID;
    private String thumbnail;
    private String categoryID;
    private String conditions;
    private String typeID;
    private String productName;
    private String sellerName;
    private String price;
    private String dateTime;
    private String time;
    private String location;
    private String description;
    private String phoneNumbers;

    public WishListItem() {
    }

    public WishListItem(String id, String sellerID, String thumbnail, String categoryID, String conditions, String typeID, String productName, String sellerName, String price, String dateTime, String time, String location, String description, String phoneNumbers) {
        this.id = id;
        this.sellerID = sellerID;
        this.thumbnail = thumbnail;
        this.categoryID = categoryID;
        this.conditions = conditions;
        this.typeID = typeID;
        this.productName = productName;
        this.sellerName = sellerName;
        this.price = price;
        this.dateTime = dateTime;
        this.time = time;
        this.location = location;
        this.description = description;
        this.phoneNumbers = phoneNumbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
