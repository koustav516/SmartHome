package com.smarthome.models;

public class SalesReport {
    private String productName;
    private double price;
    private int itemsSold;
    private double totalSales;

    public SalesReport(String productName, double price, int itemsSold, double totalSales) {
        this.productName = productName;
        this.price = price;
        this.itemsSold = itemsSold;
        this.totalSales = totalSales;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getItemsSold() {
        return itemsSold;
    }

    public double getTotalSales() {
        return totalSales;
    }
}
