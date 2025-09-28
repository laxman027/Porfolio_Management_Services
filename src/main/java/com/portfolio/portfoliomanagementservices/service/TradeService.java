package com.portfolio.portfoliomanagementservices.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliomanagementservices.DTO.StockInfo;
import com.portfolio.portfoliomanagementservices.DTO.TradeRequest;
import com.portfolio.portfoliomanagementservices.model.Portfolio;
import com.portfolio.portfoliomanagementservices.model.StockTransaction;
import com.portfolio.portfoliomanagementservices.repository.PortfolioRepository;
import com.portfolio.portfoliomanagementservices.repository.StockTransactionRepository;

@Service
public class TradeService {

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private StockTransactionRepository transactionRepo;
    
    @Autowired
    private StockPriceService stockPriceService;

    public void buyStock(String username, TradeRequest request) {
        Portfolio portfolio = portfolioRepo.findByUser_Username(username)
        		.orElseThrow(() -> new RuntimeException("Portfolio not found"));

        StockInfo stockInfo=stockPriceService.getStockInfo(request.getStockSymbol());
        StockTransaction txn = new StockTransaction();
        txn.setStockSymbol(request.getStockSymbol());
        txn.setQuantity(request.getQuantity());
        txn.setPrice(stockInfo.getPrice());
        txn.setTransactionType("BUY");
        txn.setTimestamp(LocalDateTime.now());
        txn.setPortfolio(portfolio);

        transactionRepo.save(txn);
    }

    public void sellStock(String username, TradeRequest request) {
        Portfolio portfolio = portfolioRepo.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        // Optional: check if user has enough quantity to sell

        StockInfo stockInfo=stockPriceService.getStockInfo(request.getStockSymbol());
        StockTransaction txn = new StockTransaction();
        txn.setStockSymbol(request.getStockSymbol());
        txn.setQuantity(request.getQuantity());
        txn.setPrice(stockInfo.getPrice());
        txn.setTransactionType("SELL");
        txn.setTimestamp(LocalDateTime.now());
        txn.setPortfolio(portfolio);

        transactionRepo.save(txn);
    }
}
