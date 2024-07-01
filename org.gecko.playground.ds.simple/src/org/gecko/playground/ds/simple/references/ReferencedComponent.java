package org.gecko.playground.ds.simple.references;

import org.gecko.playground.log.Log;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

//Remove comment to get this work ;-)
//@Component
public class ReferencedComponent {
	
	public ReferencedComponent () {
		System.out.println("ReferencedComponent created");
	}
	
	@Reference
	void setLog(Log log) {
		System.out.println("set Log " + log.getClass().getName());
	}
	
	void unsetLog(Log log) {
		System.out.println("unset Log " + log.getClass().getName());
	}

}
