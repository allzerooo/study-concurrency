package com.study.concurrency.stockservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Stock {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;

	private Long quantity;

	public Stock() {
	}

	public Stock(final Long productId, final Long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void decrease(final Long quantity) {
		if (this.quantity - quantity < 0) {
			throw new RuntimeException("재고가 부족합니다");
		}

		this.quantity = this.quantity - quantity;
	}
}
