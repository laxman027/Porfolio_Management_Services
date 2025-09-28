package com.portfolio.portfoliomanagementservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.portfoliomanagementservices.model.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
	
	Optional<Portfolio> findByUser_Username(String username);

}
