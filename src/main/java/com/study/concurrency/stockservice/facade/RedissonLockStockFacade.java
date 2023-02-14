package com.study.concurrency.stockservice.facade;

import com.study.concurrency.stockservice.domain.StockService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockStockFacade {

	private final RedissonClient redissonClient;
	private final StockService stockService;

	public void decrease(final Long id, final Long quantity) {
		final RLock lock = redissonClient.getLock(id.toString());

		try {
			final boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

			if (!available) {
				log.info("lock 획득 실패");
				return;
			}

			stockService.decrease(id, quantity);

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}
}
