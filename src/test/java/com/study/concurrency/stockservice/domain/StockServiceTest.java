package com.study.concurrency.stockservice.domain;

import static org.assertj.core.api.Assertions.*;

import com.study.concurrency.stockservice.infrastructure.StockRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTest {

	@Autowired StockService stockService;
	@Autowired PessimisticLockStockService pessimisticLockStockService;
	@Autowired StockRepository stockRepository;

	@BeforeEach
	void beforeEach() {
		final Stock stock = new Stock(1L, 100L);
		stockRepository.save(stock);
	}

	@AfterEach
	void afterEach() {
		stockRepository.deleteAll();
	}

	@Test
	void decreaseStock() {
	  // when
		stockService.decrease(1L, 1L);

	  // then
		final Stock stock = stockRepository.findById(1L).orElseThrow();
		assertThat(stock.getQuantity()).isEqualTo(99L);
	}

	@Test
	void decreaseStockRequest100Times() throws InterruptedException {
	  // given
	  int threadCount = 100;
		final ExecutorService executorService = Executors.newFixedThreadPool(32);
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					stockService.decrease(1L, 1L);
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
	  
	  // then
		final Stock stock = stockRepository.findById(1L).orElseThrow();
		assertThat(stock.getQuantity()).isEqualTo(0L);
	}

	@Test
	void decreaseStockRequest100TimesWithPessimisticLock() throws InterruptedException {
		// given
		int threadCount = 100;
		final ExecutorService executorService = Executors.newFixedThreadPool(32);
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					pessimisticLockStockService.decrease(1L, 1L);
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();

		// then
		final Stock stock = stockRepository.findById(1L).orElseThrow();
		assertThat(stock.getQuantity()).isEqualTo(0L);
	}
}