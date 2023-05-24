/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.gecko.playground.ds.config.api.Album;
import org.gecko.playground.ds.simple.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Scalar;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;

/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class ExampleTest {

	@Mock
	Bar mockBar;
	private BundleContext ctx;

	@BeforeEach
	public void before(@InjectBundleContext BundleContext ctx) {
		this.ctx = ctx;
	}

	@Test
	public void testLogServiceAware(@InjectService ServiceAware<Log> logAware) {
		assertFalse(logAware.isEmpty());
		Log log = logAware.getService();
		assertNotNull(log);
		assertNotNull(mockBar);
	}

	@Test
	public void testLogService(@InjectService Log log) {
		assertNotNull(log);
		assertNotNull(mockBar);
	}
	
	@Test
	public void testLogInfo(@InjectService Log log) {
		assertNotNull(log);
		assertNotNull(mockBar);
		assertEquals("INFO: test", log.info("test"));
	}

	@Test
	public void testBarServicePublished(@InjectService(cardinality = 0) ServiceAware<Bar> barAware, 
			@InjectBundleContext BundleContext context) throws Exception {

		// Precondition: no Bar service
		assertTrue(barAware.isEmpty());

		// Publish Bar service
		ServiceRegistration<Bar> reg = context.registerService(Bar.class, mock(Bar.class), null);
		
		// Postcondition: Bar service now exists
		try {
			Bar bar = barAware.getService();
			assertNotNull(bar);
		} finally {
			reg.unregister();
			assertTrue(barAware.isEmpty());
		}
	}
	
	@Test
	@WithFactoryConfiguration(name = "pinkFloyd", location = "?", factoryPid = "MyAlbumConfig", properties = {
			@Property(key = "name", value = "Pink Floyd"),
			@Property(key = "year", scalar = Scalar.Integer, value = "1977"),
			@Property(key = "album", value = "Dark Side of the Moon")})
	public void testAlbum(@InjectService(filter = "(year=1977)") Album album) {
		assertEquals("Pink Floyd - Dark Side of the Moon (1977)", album.getAlbum());
	}
	
	@Test
	@WithFactoryConfiguration(name = "pinkFloyd", location = "?", factoryPid = "MyAlbumConfig", properties = {
			@Property(key = "name", value = "Pink Floyd"),
			@Property(key = "year", scalar = Scalar.Integer, value = "1977"),
			@Property(key = "album", value = "Dark Side of the Moon")})
	public void testAlbumOptional(@InjectService(cardinality = 0, filter = "(year=1977)") ServiceAware<Album> albumAware) {
		assertFalse(albumAware.isEmpty());
		Album album;
		try {
			album = albumAware.waitForService(1000l);
			assertEquals("Pink Floyd - Dark Side of the Moon (1977)", album.getAlbum());
		} catch (InterruptedException e) {
			fail("Album service not available for year = 1977");
		}
	}
}
