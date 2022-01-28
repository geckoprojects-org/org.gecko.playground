package org.gecko.test.impl;

import org.gecko.test.api.TestApi;
import org.gecko.test.api.TestCore;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

@Component
public class TestImpl implements TestApi {
	
	@Reference
	private TestCore core;
	
	@Activate
	public void activate() {
		System.out.println("Impl activate");
	}
	
	@Modified
	public void modified() {
		System.out.println("Impl modified");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Impl deactivate");
	}

	@Override
	public void log(String msg) {
		core.logMe(msg);
	}

}
