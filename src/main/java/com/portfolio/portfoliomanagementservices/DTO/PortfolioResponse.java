package com.portfolio.portfoliomanagementservices.DTO;

public class PortfolioResponse {

	 	private String stockSymbol;
	 	private int quantity;
	    private double currentPrice;
	    private double totalValue;
	    
	    
	    
	    //Setters & Getters
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
		public double getCurrentPrice() {
			return currentPrice;
		}
		public void setCurrentPrice(double currentPrice) {
			this.currentPrice = currentPrice;
		}
		public double getTotalValue() {
			return totalValue;
		}
		public void setTotalValue(double totalValue) {
			this.totalValue = totalValue;
		}
		
}
