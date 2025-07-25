package com.smarthome.models;

import org.bson.types.ObjectId;

public class ProductReviews {

    private ObjectId id;
    private int productID;
    private String productModelName;
    private String productCategory;
    private double productPrice;
    private String storeLocation;
    private int userID;
    private String userName;
    private int reviewRating;
    private String reviewDate;
    private String reviewText;

    public ProductReviews(int productID, String productModelName, String productCategory, double productPrice,
            String storeLocation,
            int userID, String userName, int reviewRating, String reviewDate,
            String reviewText) {
        this.productID = productID;
        this.productModelName = productModelName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.storeLocation = storeLocation;
        this.userID = userID;
        this.userName = userName;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getProductModelName() {
        return productModelName;
    }

    public void setProductModelName(String productModelName) {
        this.productModelName = productModelName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

}
