package com.lymin.nexonlinemarket.user;

public class UsersModel {

    private String id;
    private String name;
    private String profile;
    private String address;
    private String gander;
    private String dateOfBirth;
    private String about;
    private String email;
    private String age;
    private double latitude;
    private double longitude;

    public UsersModel() {
    }

    public UsersModel(String id, String name, String profile, String address, String gander, String dateOfBirth, String about, String email, String age, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.address = address;
        this.gander = gander;
        this.dateOfBirth = dateOfBirth;
        this.about = about;
        this.email = email;
        this.age = age;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
