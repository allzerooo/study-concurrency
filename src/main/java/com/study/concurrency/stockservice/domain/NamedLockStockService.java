package com.study.concurrency.stockservice.domain;

import com.study.concurrency.stockservice.infrastructure.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NamedLockStockService {

	private final StockRepository stockRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decrease(final Long id, final Long quantity) {
		// lock을 걸고 stock 조회
		final Stock stock = stockRepository.findByIdWithPessimisticLock(id);
		stock.decrease(quantity);
	}
}
