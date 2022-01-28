package org.gecko.test.core;

import org.gecko.test.api.TestCore;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

@Component
public class TestCoreImpl implements TestCore {
	
	@Activate
	public void activate() {
		System.out.println("Core activate");
	}
	
	@Modified
	public void modified() {
		System.out.println("Core modified");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Core deactivate");
	}
	
	public void logMe(String msg) {
		System.out.println("Log: " + msg);
	}

}
