package com.study.concurrency.threadsafety;

@NotThreadSafe
public class UnsafeSequence {

	private int value;

	/** 유일한 값을 리턴 */
	public int getNext() {
		return value++;
	}

}
