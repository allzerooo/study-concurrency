package com.study.concurrency.threadsafety;

import com.study.concurrency.annotation.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {

	// 1. 스레드 A와 B가 동시에 instace가 null이라는 것을 확인
	private ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
		if (instance == null)
			// 2. 항상 같은 인스턴스를 리턴하도록 설계했지만 스레드 A와 B 모두 새로운 인스턴스를 생성하게 될 수 있음
			instance = new ExpensiveObject();
		return instance;
	}
}

class ExpensiveObject { }
