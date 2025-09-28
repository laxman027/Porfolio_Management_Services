package com.portfolio.portfoliomanagementservices.DTO;

import java.util.List;

public class PortfolioSummaryResponse {
    private List<PortfolioResponse> holdings;
    private double totalValue;

    // Constructors
    public PortfolioSummaryResponse() {}

    public PortfolioSummaryResponse(List<PortfolioResponse> holdings, double totalValue) {
        this.holdings = holdings;
        this.totalValue = totalValue;
    }

    // Getters and Setters
    public List<PortfolioResponse> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<PortfolioResponse> holdings) {
        this.holdings = holdings;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
