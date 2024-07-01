package org.gecko.playground.ds.optional.impl;

import static java.util.Objects.isNull;

import java.util.concurrent.atomic.AtomicReference;

import javax.sql.DataSource;

import org.gecko.playground.ds.optional.CustomerManagerService;
import org.gecko.playground.ds.optional.DataSourceService;
import org.gecko.playground.log.Log;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(immediate = true)
public class CustomerManager implements CustomerManagerService {
	
	private AtomicReference<Log> logRef = new AtomicReference<>();
	private DataSourceService datasource;
	
	public CustomerManager() {
		System.out.println("Create customer manager");
	}
	
	@Activate
	public void activate() {
		System.out.println("Activate customer manager service component");
		Log log = logRef.get();
		if (isNull(log)) {
			System.out.println("Log not available!");
		} else {
			log.info("Hello Customer Manager");
		}
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("De-Activate customer manager service component");
	}
	
	@Reference
	public void setDataSource(DataSourceService datasource) {
		System.out.println("Set datasource " + datasource.toString());
		this.datasource = datasource;
	}
	
	public void unsetDataSource(DataSourceService datasource) {
		System.out.println("Unset datasource " + datasource.toString());
		this.datasource = null;
	}
	
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
//	@Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
	public void setLog(Log log) {
		System.out.println("Set Log " + log.toString());
		logRef.compareAndSet(null, log);
// 		When doing service exchange, first set will be called		
//		logRef.set(log);
	}
	
	public void unsetLog(Log log) {
		System.out.println("Unset Log " + log.toString());
		logRef.compareAndSet(log, null);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.dynamics.api.CustomerManagerService#doWithCustomer()
	 */
	@Override
	public void doWithCustomer() {
		DataSource ds = datasource.getDataSource();
	}

}
