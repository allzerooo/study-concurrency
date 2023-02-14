package com.study.concurrency.stockservice.facade;

import com.study.concurrency.stockservice.domain.StockService;
import com.study.concurrency.stockservice.infrastructure.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

	private final RedisRepository redisRepository;
	private final StockService stockService;

	public void decrease(final Long id, final Long quantity) throws InterruptedException {
		// lock 획득을 계속 시도
		while (!redisRepository.lock(id)) {
			Thread.sleep(100);
		}

		// lock을 획득했으면 재고 감소
		try {
			stockService.decrease(id, quantity);
		} finally {
			// lock 해제
			redisRepository.unlock(id);
		}
	}
}
