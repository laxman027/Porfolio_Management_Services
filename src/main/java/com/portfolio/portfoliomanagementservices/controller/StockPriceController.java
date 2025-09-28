package com.portfolio.portfoliomanagementservices.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliomanagementservices.DTO.StockInfo;
import com.portfolio.portfoliomanagementservices.service.StockPriceService;

@RestController
@RequestMapping("/api")
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    @GetMapping("/price/{symbol}")
    public ResponseEntity<?> getStockInfo(@PathVariable String symbol) {
        StockInfo stockInfo = stockPriceService.getStockInfo(symbol);

        if (stockInfo.getPrice() == -1.0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not fetch price for: " + symbol);
        }

        return ResponseEntity.ok(stockInfo);
    }
}