package org.gecko.playground.maven.ds.simple;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.gecko.playground.ds.simple.ConsoleLog;
import org.gecko.playground.ds.simple.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SimpleTest {
	
	@Test
	public void testLog() {
		Log log = new ConsoleLog();
		assertNotNull(log);
	}
	
	@Test
	public void testLogMock() {
		Log log = new ConsoleLog();
		Log logMock = mock(Log.class);
		assertNotEquals(log, logMock);
	}

}
