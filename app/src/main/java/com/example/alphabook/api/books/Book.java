
package com.example.alphabook.api.books;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    //cart

    @SerializedName("cart_id")
    @Expose
    private String cartID;

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }


    @SerializedName("pay_id")
    @Expose
    private String payId;


    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    @SerializedName("book_id")
    @Expose
    private String bookId;
    @SerializedName("book_title")
    @Expose
    private String bookTitle;
    @SerializedName("book_rating")
    @Expose
    private String bookRating;
    @SerializedName("book_category")
    @Expose
    private String bookCategory;
    @SerializedName("book_publisher")
    @Expose
    private String bookPublisher;
    @SerializedName("book_edition")
    @Expose
    private String bookEdition;
    @SerializedName("book_author")
    @Expose
    private String bookAuthor;
    @SerializedName("book_amount")
    @Expose
    private String bookAmount;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("book_status")
    @Expose
    private String bookStatus;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("univerboard")
    @Expose
    private String univerboard;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_cat_1")
    @Expose
    private String subCat1;
    @SerializedName("sub_cat_2")
    @Expose
    private String subCat2;
    @SerializedName("sub_cat_3")
    @Expose
    private String subCat3;
    @SerializedName("sub_cat_4")
    @Expose
    private String subCat4;
    @SerializedName("sub_cat_5")
    @Expose
    private Object subCat5;
    @SerializedName("sub_cat_6")
    @Expose
    private Object subCat6;
    @SerializedName("sub_cat_7")
    @Expose
    private Object subCat7;
    @SerializedName("sub_cat_8")
    @Expose
    private Object subCat8;
    @SerializedName("sub_cat_9")
    @Expose
    private Object subCat9;
    @SerializedName("sub_cat_10")
    @Expose
    private Object subCat10;
    @SerializedName("img1")
    @Expose
    private String img1;
    @SerializedName("img2")
    @Expose
    private String img2;
    @SerializedName("img3")
    @Expose
    private String img3;
    @SerializedName("img4")
    @Expose
    private String img4;
    @SerializedName("img5")
    @Expose
    private String img5;
    @SerializedName("img6")
    @Expose
    private String img6;
    @SerializedName("img7")
    @Expose
    private String img7;
    @SerializedName("img8")
    @Expose
    private String img8;
    @SerializedName("img9")
    @Expose
    private String img9;
    @SerializedName("img10")
    @Expose
    private String img10;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("phoneno")
    @Expose
    private String phoneno;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("social_link")
    @Expose
    private Object socialLink;
    @SerializedName("password")
    @Expose
    private String password;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookRating() {
        return bookRating;
    }

    public void setBookRating(String bookRating) {
        this.bookRating = bookRating;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(String bookAmount) {
        this.bookAmount = bookAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUniverboard() {
        return univerboard;
    }

    public void setUniverboard(String univerboard) {
        this.univerboard = univerboard;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCat1() {
        return subCat1;
    }

    public void setSubCat1(String subCat1) {
        this.subCat1 = subCat1;
    }

    public String getSubCat2() {
        return subCat2;
    }

    public void setSubCat2(String subCat2) {
        this.subCat2 = subCat2;
    }

    public String getSubCat3() {
        return subCat3;
    }

    public void setSubCat3(String subCat3) {
        this.subCat3 = subCat3;
    }

    public String getSubCat4() {
        return subCat4;
    }

    public void setSubCat4(String subCat4) {
        this.subCat4 = subCat4;
    }

    public Object getSubCat5() {
        return subCat5;
    }

    public void setSubCat5(Object subCat5) {
        this.subCat5 = subCat5;
    }

    public Object getSubCat6() {
        return subCat6;
    }

    public void setSubCat6(Object subCat6) {
        this.subCat6 = subCat6;
    }

    public Object getSubCat7() {
        return subCat7;
    }

    public void setSubCat7(Object subCat7) {
        this.subCat7 = subCat7;
    }

    public Object getSubCat8() {
        return subCat8;
    }

    public void setSubCat8(Object subCat8) {
        this.subCat8 = subCat8;
    }

    public Object getSubCat9() {
        return subCat9;
    }

    public void setSubCat9(Object subCat9) {
        this.subCat9 = subCat9;
    }

    public Object getSubCat10() {
        return subCat10;
    }

    public void setSubCat10(Object subCat10) {
        this.subCat10 = subCat10;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getImg6() {
        return img6;
    }

    public void setImg6(String img6) {
        this.img6 = img6;
    }

    public String getImg7() {
        return img7;
    }

    public void setImg7(String img7) {
        this.img7 = img7;
    }

    public String getImg8() {
        return img8;
    }

    public void setImg8(String img8) {
        this.img8 = img8;
    }

    public String getImg9() {
        return img9;
    }

    public void setImg9(String img9) {
        this.img9 = img9;
    }

    public String getImg10() {
        return img10;
    }

    public void setImg10(String img10) {
        this.img10 = img10;
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

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
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

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Object getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(Object socialLink) {
        this.socialLink = socialLink;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}