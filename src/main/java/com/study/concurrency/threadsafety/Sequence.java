package com.study.concurrency.threadsafety;

@ThreadSafe
public class Sequence {

	@GuardedBy("this") private int value;

	public synchronized int getNext() {
		return value;
	}

}
