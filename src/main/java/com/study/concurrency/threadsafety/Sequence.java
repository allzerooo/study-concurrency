package com.study.concurrency.threadsafety;

import com.study.concurrency.annotation.GuardedBy;
import com.study.concurrency.annotation.ThreadSafe;

@ThreadSafe
public class Sequence {

	@GuardedBy("this") private int value;

	public synchronized int getNext() {
		return value;
	}

}
