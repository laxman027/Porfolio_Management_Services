package com.portfolio.portfoliomanagementservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.portfoliomanagementservices.model.StockTransaction;

import jakarta.transaction.Transaction;


@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    List<StockTransaction> findByPortfolioId(Long porfolioId);
    List<StockTransaction> findByPortfolio_IdOrderByTimestampDesc(Long portfolioId);
}