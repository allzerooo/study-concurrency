package com.study.concurrency.stockservice.infrastructure;

import com.study.concurrency.stockservice.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
