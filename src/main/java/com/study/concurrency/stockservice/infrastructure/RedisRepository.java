package com.study.concurrency.stockservice.infrastructure;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {

	private final RedisTemplate redisTemplate;

	public Boolean lock(final Long key) {
		return redisTemplate
				.opsForValue()
				.setIfAbsent(generatedKey(key), "lock", Duration.ofMillis(3_000));
	}

	public Boolean unlock(final Long key) {
		return redisTemplate
				.delete(generatedKey(key));
	}

	private String generatedKey(final Long key) {
		return key.toString();
	}
}
