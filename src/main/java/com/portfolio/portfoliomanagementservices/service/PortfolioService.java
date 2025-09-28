package com.portfolio.portfoliomanagementservices.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliomanagementservices.DTO.PortfolioResponse;
import com.portfolio.portfoliomanagementservices.DTO.PortfolioSummaryResponse;
import com.portfolio.portfoliomanagementservices.DTO.StockInfo;
import com.portfolio.portfoliomanagementservices.DTO.TransactionResponse;
import com.portfolio.portfoliomanagementservices.model.Portfolio;
import com.portfolio.portfoliomanagementservices.model.StockTransaction;
import com.portfolio.portfoliomanagementservices.repository.PortfolioRepository;
import com.portfolio.portfoliomanagementservices.repository.StockTransactionRepository;


@Service
public class PortfolioService {
	
	@Autowired
    private PortfolioRepository portfolioRepository;
	
	@Autowired
	private StockTransactionRepository stockTransactionRepository;
	
	@Autowired
	private StockPriceService stockPriceService;
	

    public Optional<Portfolio> getUserPortfolio(String username){
    	
		return portfolioRepository.findByUser_Username(username);
    	
    }
    
    public Portfolio savePortfolio(Portfolio portfolio) {
		return portfolioRepository.save(portfolio);
    	
    }
    
    public PortfolioSummaryResponse calculatePortfolio(Long id) {
    	 List<StockTransaction> transactions = stockTransactionRepository.findByPortfolioId(id);
        
        // Map to store net quantity
        Map<String, Integer> stockQuantityMap = new HashMap<>();
        
        for (StockTransaction tx : transactions) {
            int qty = tx.getQuantity();
            if (tx.getTransactionType().equalsIgnoreCase("SELL")) {
                qty *= -1;
            }
            stockQuantityMap.put(tx.getStockSymbol(),
                stockQuantityMap.getOrDefault(tx.getStockSymbol(), 0) + qty);
        }

        List<PortfolioResponse> response = new ArrayList<>();
        double totalValue = 0.0;

        for (Map.Entry<String, Integer> entry : stockQuantityMap.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();

            if (quantity <= 0) continue; // skip fully sold stocks

            StockInfo stockInfo = stockPriceService.getStockInfo(symbol); // call API
            double currentPrice = stockInfo.getPrice();
            double stockValue = quantity * currentPrice;
            totalValue += stockValue;

            PortfolioResponse pr = new PortfolioResponse();
            pr.setStockSymbol(symbol);
            pr.setQuantity(quantity);
            pr.setCurrentPrice(currentPrice);
            pr.setTotalValue(stockValue);

            response.add(pr);
        }

        return new PortfolioSummaryResponse(response,totalValue);
    }
    
    public List<TransactionResponse> getTransactionHistory(Long id) {
        List<StockTransaction> transactions = stockTransactionRepository.findByPortfolio_IdOrderByTimestampDesc(id);

        return transactions.stream()
        	    .map(tx -> new TransactionResponse(
        	        tx.getStockSymbol(),
        	        tx.getQuantity(),
        	        tx.getPrice(),
        	        tx.getTransactionType(),
        	        tx.getTimestamp()))
        	    .collect(Collectors.toList());
    }



}
