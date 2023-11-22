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
package de.gecko.playground.ds.inject;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * 
 * @author mark
 * @since 14.11.2023
 */
@Component(name="TestConfig", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class Test implements ITest {
	
	private AtomicReference<Config> config = new AtomicReference<>();

	@interface Config {
		String salutation();
	}
	
	@Activate
	public void activate(Config config) {
		this.config.compareAndSet(null, config);
		System.out.println("activate config");
	}
	
	@Modified
	public void modified(Config config) {
		Config current = this.config.get();
		this.config.compareAndSet(current, config);
		System.out.println("modified config");
	}

	/* 
	 * (non-Javadoc)
	 * @see de.gecko.playground.ds.inject.ITest#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String name) {
		return config.get().salutation() + " " + name;
	}

}
