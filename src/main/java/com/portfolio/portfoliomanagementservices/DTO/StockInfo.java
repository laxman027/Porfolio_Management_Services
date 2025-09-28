package com.portfolio.portfoliomanagementservices.DTO;

public class StockInfo {
    private double price;
    private String currency;

    public StockInfo(double price, String currency) {
        this.price = price;
        this.currency = currency;
    }

    public double getPrice() { return price; }
    public String getCurrency() { return currency; }
}
