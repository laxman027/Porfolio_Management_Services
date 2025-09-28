package com.portfolio.portfoliomanagementservices.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliomanagementservices.DTO.PortfolioResponse;
import com.portfolio.portfoliomanagementservices.DTO.PortfolioSummaryResponse;
import com.portfolio.portfoliomanagementservices.DTO.TransactionResponse;
import com.portfolio.portfoliomanagementservices.model.Portfolio;
import com.portfolio.portfoliomanagementservices.model.StockTransaction;
import com.portfolio.portfoliomanagementservices.model.User;
import com.portfolio.portfoliomanagementservices.repository.PortfolioRepository;
import com.portfolio.portfoliomanagementservices.repository.StockTransactionRepository;
import com.portfolio.portfoliomanagementservices.repository.UserRepository;
import com.portfolio.portfoliomanagementservices.service.PortfolioService;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepo;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private StockTransactionRepository transactionRepo;
    
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<?> getPortfolioSummary(Principal principal) {
        Portfolio portfolio = portfolioRepo.findByUser_Username(principal.getName())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        List<StockTransaction> transactions = transactionRepo.findByPortfolioId(portfolio.getId());

        Map<String, Integer> holdings = new HashMap<>();

        for (StockTransaction txn : transactions) {
            String symbol = txn.getStockSymbol();
            int qty = holdings.getOrDefault(symbol, 0);

            if (txn.getTransactionType().equalsIgnoreCase("BUY")) {
                qty += txn.getQuantity();
            } else {
                qty -= txn.getQuantity();
            }

            holdings.put(symbol, qty);
        }

        return ResponseEntity.ok(holdings);
    }
    
    
    @GetMapping("/value")
    public ResponseEntity<PortfolioSummaryResponse> getPortfolio(Principal principal) {
    	Portfolio port = portfolioRepo.findByUser_Username(principal.getName())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    	PortfolioSummaryResponse portfolio = portfolioService.calculatePortfolio(port.getId());
        return ResponseEntity.ok(portfolio);
    }
    
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory( Principal principal) {
    	Portfolio portfolio = portfolioRepo.findByUser_Username(principal.getName())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        List<TransactionResponse> history = portfolioService.getTransactionHistory(portfolio.getId());
        return ResponseEntity.ok(history);
    }
   
    @PostMapping
    public ResponseEntity<?> createPortfolio(Principal principal) {
        String username = principal.getName();
        User user = userRepo.findByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found"));

        if (portfolioRepo.findByUser_Username(username).isPresent()) {
            return ResponseEntity.badRequest().body("Portfolio already exists");
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolioRepo.save(portfolio);

        return ResponseEntity.ok("Portfolio created successfully");
    }
}
