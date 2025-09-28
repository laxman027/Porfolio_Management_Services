package com.portfolio.portfoliomanagementservices.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliomanagementservices.DTO.TradeRequest;
import com.portfolio.portfoliomanagementservices.service.TradeService;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(@RequestBody TradeRequest request, Principal principal) {
        tradeService.buyStock(principal.getName(), request);
        return ResponseEntity.ok("Stock bought successfully");
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@RequestBody TradeRequest request, Principal principal) {
        tradeService.sellStock(principal.getName(), request);
        return ResponseEntity.ok("Stock sold successfully");
    }
}