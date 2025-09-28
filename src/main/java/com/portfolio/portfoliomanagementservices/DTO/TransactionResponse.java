package com.portfolio.portfoliomanagementservices.DTO;

import java.time.LocalDateTime;

public class TransactionResponse {
    private String stockSymbol;
    private int quantity;
    private double price;
    private String type;
    private LocalDateTime date;

    // Constructor
    public TransactionResponse(String stockSymbol, int quantity, double price, String type, LocalDateTime date) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.date = date;
    }

    //Setters & gettters
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

    
}
