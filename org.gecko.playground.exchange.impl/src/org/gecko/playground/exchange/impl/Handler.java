package org.gecko.playground.exchange.impl;

public interface Handler<T> {
	void handle(T event) throws Exception;
}
