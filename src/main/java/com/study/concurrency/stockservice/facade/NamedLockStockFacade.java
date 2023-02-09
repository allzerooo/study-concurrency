package com.study.concurrency.stockservice.facade;

import com.study.concurrency.stockservice.domain.NamedLockStockService;
import com.study.concurrency.stockservice.domain.OptimisticLockStockService;
import com.study.concurrency.stockservice.infrastructure.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

	private final LockRepository lockRepository;
	private final NamedLockStockService namedLockStockService;

	public void decrease(final Long id, final Long quantity) throws InterruptedException {
		try {
			lockRepository.getLock(id.toString());
			namedLockStockService.decrease(id, quantity);
		} finally {
			lockRepository.release(id.toString());
		}
	}

}
