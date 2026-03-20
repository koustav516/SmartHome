package com.smarthome.models;

import com.mysql.cj.jdbc.Blob;

public class Ticket {

    private String ticketNumber;
    private int userId;
    private String productDetails;
    private byte[] image;
    private String decision;

    public Ticket(String ticketNumber, int userId, String productDetails, byte[] image) {
        this.ticketNumber = ticketNumber;
        this.userId = userId;
        this.productDetails = productDetails;
        this.image = image;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDecision() {
        return decision;
    }
}
