package org.gecko.playground.promise;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

public class ExampleTest {

	@Test
	public void testSuccess() throws InvocationTargetException, InterruptedException {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<String> sp = pf.resolved("test").recover(p->{
			return "resolved";
		});
		assertEquals("test", sp.getValue());
	}
	
	@Test
	public void testRecover() throws InvocationTargetException, InterruptedException {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<Object> sp = pf.failed(new NullPointerException()).recover(p->{
			return "resolved";
		});
		assertEquals("resolved", sp.getValue());
	}
	
	@Test
	public void testNoErrorInCallable() throws InvocationTargetException, InterruptedException {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<String> sp = pf.submit(()->{
			return "test";
		}).recover(p->{
			return "resolved";
		});
		assertEquals("test", sp.getValue());
	}
	
	@Test
	public void testErrorInCallable() throws InvocationTargetException, InterruptedException {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<Object> sp = pf.submit(()->{
			throw new NullPointerException();
		}).recover(p->{
			return "resolved";
		});
		assertEquals("resolved", sp.getValue());
	}
	
	@Test
	public void testErrorInCallableThrowable() throws InvocationTargetException, InterruptedException {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<Object> sp = pf.submit(()->{
			throw new NullPointerException();
		}).recover(p->{
			Throwable t = p.getFailure();
			if (t instanceof NullPointerException) {
				return new IllegalArgumentException(t);
			} else {
				return t;
			}
		});
		assertTrue(sp.getValue() instanceof IllegalArgumentException);
		assertNull(sp.getFailure());
	}
	
	@Test
	public void testErrorInCallableNoRecover() {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<Object> sp = pf.submit(()->{
			throw new NullPointerException();
		});
		try {
			sp.getValue();
			fail("No value expected");
		} catch (InvocationTargetException e) {
			assertTrue(e.getTargetException() instanceof NullPointerException);
		} catch (InterruptedException e) {
			fail("This is not expected");
		}
		try {
			assertTrue(sp.getFailure() instanceof NullPointerException);
		} catch (InterruptedException e) {
			fail("This is not expected");
		}
	}
	
	@Test
	public void testErrorInCallableRecoverISE() {
		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		
		Promise<Object> sp = pf.submit(()->{
			throw new NullPointerException();
		}).recover(p->{
			Throwable t = p.getFailure();
			if (t instanceof NullPointerException) {
				throw new IllegalArgumentException(t);
			} else {
				return t;
			}
		});
		try {
			sp.getValue();
			fail("No value expected");
		} catch (InvocationTargetException e) {
			assertTrue(e.getTargetException() instanceof IllegalArgumentException);
		} catch (InterruptedException e) {
			fail("This is not expected");
		}
		try {
			assertTrue(sp.getFailure() instanceof IllegalArgumentException);
		} catch (InterruptedException e) {
			fail("This is not expected");
		}
	}

}
