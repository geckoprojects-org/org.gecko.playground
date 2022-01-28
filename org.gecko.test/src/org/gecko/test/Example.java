package org.gecko.test;

import org.gecko.test.api.TestApi;
import org.osgi.service.component.annotations.*;

@Component
public class Example {
	
	@Activate
	public void activate() {
		System.out.println("Example activate");
	}
	
	@Modified
	public void modified() {
		System.out.println("Example modified");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Example deactivate");
	}
	
	@Reference(cardinality = ReferenceCardinality.OPTIONAL, unbind = "unsetApi", updated = "modifyApi", policy = ReferencePolicy.DYNAMIC)
	public void setApi(TestApi service) {
		System.out.println("Example set API " + service);
	}
	
	public void unsetApi(TestApi service) {
		System.out.println("Example unset API " + service);
	}
	
	public void modifyApi(TestApi service) {
		System.out.println("Example modify API " + service);
	}

}
