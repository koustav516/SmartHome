package com.smarthome.models;

public class ProductReviewCount {
    private int productId;
    private String productName;
    private long reviewCount;

    public ProductReviewCount() {

    }

    public ProductReviewCount(int productId, String productName, long reviewCount) {
        this.productId = productId;
        this.productName = productName;
        this.reviewCount = reviewCount;
    }

    public int getProductId() {
        return productId;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
