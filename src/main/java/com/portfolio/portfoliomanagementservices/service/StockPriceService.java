package com.portfolio.portfoliomanagementservices.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.portfolio.portfoliomanagementservices.DTO.StockInfo;

import org.json.JSONObject;

@Service
public class StockPriceService {

	@Value("${finnhub.api.key}")
	private String apiKey;

	private final String BASE_URL = "https://finnhub.io/api/v1/quote";

	 private final RestTemplate restTemplate = new RestTemplate();
	
	 public StockInfo getStockInfo(String symbol) {
		 try {
			 String quoteUrl = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;
			 ResponseEntity<String> quoteResponse = restTemplate.getForEntity(quoteUrl, String.class);
	            JSONObject quoteJson = new JSONObject(quoteResponse.getBody());
	            double price = quoteJson.getDouble("c");

	            String profileUrl = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=" + apiKey;
	            ResponseEntity<String> profileResponse = restTemplate.getForEntity(profileUrl, String.class);
	            JSONObject profileJson = new JSONObject(profileResponse.getBody());
	            String currency = profileJson.optString("currency", "N/A");

			 return new StockInfo(price, currency);
		 }catch(Exception e) {
			 e.printStackTrace();
			 return new StockInfo(-1, "N/A");
		 }
		
		 
	 }

	   
	    
}