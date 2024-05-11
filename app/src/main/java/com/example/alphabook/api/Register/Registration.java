package com.example.alphabook.api.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registration {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("phoneno")
    @Expose
    private String phoneno;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("profile_picture")
    @Expose
    private Object profilePicture;
    @SerializedName("social_link")
    @Expose
    private String socialLink;

    @SerializedName("password")
    @Expose
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Object getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Object profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(String socialLink) {
        this.socialLink = socialLink;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("f_comment")
    @Expose
    private String fComment;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getfComment() {
        return fComment;
    }

    public void setfComment(String fComment) {
        this.fComment = fComment;
    }

}