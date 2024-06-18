/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.playground.ds.simple;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.gecko.playground.log.Log;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * 
 * @author mark
 * @since 21.05.2024
 */
//@Component(name = "MyFL", service = Log.class, properties = "foo=bar", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class MyFancyLog extends ConsoleLog {
	
	private AtomicReference<Log> delegateLog = new AtomicReference<>();
	
	/**
	 * Creates a new instance.
	 */
	@Activate
	public MyFancyLog(@Reference ConfigurationAdmin admin, Map<String, Object> properties) {
		setCa(admin);
	}
	
	@Deactivate
	public void deactivate() {
		
	}
	

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.simple.ConsoleLog#setCa(org.osgi.service.cm.ConfigurationAdmin)
	 */
	@Override
	@Reference
	public void setCa(ConfigurationAdmin ca) {
		// TODO Auto-generated method stub
		super.setCa(ca);
	}

	/**
	 * Returns the delegateLog.
	 * @return the delegateLog
	 */
	public Log getDelegateLog() {
		return delegateLog.get();
	}

	/**
	 * Sets the delegateLog.
	 * @param delegateLog the delegateLog to set
	 */
	@Reference(policy = ReferencePolicy.DYNAMIC)
	public void setDelegateLog(Log delegateLog) {
		this.delegateLog.compareAndSet(null, delegateLog);
	}
	
	public void unsetDelegateLog(Log delegateLog) {
		this.delegateLog.compareAndSet(delegateLog, null);
	}
}
