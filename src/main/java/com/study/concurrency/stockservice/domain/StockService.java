package com.study.concurrency.stockservice.domain;

import com.study.concurrency.stockservice.infrastructure.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(final Long id, final Long quantity) {
		final Stock stock = stockRepository.findById(id).orElseThrow();
		stock.decrease(quantity);
	}
}
