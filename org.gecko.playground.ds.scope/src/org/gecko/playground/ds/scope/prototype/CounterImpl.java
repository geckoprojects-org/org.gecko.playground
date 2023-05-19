package org.gecko.playground.ds.scope.prototype;

import org.gecko.playground.ds.scope.Counter;

public class CounterImpl implements Counter {
	
	private int count = 0;

	@Override
	public int getCurrentCount() {
		return ++count;
	}

}
