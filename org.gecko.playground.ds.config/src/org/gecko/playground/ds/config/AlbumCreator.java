package org.gecko.playground.ds.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.promise.PromiseFactory;

@Component
public class AlbumCreator {

	@Reference
	private ConfigurationAdmin configAdmin;
	private List<Configuration> configs = new LinkedList<>();
	
	@Activate
	public void activate() {
		PromiseFactory pf = new PromiseFactory(Executors.newSingleThreadExecutor());
		pf.submit(this::createConfigs);
	}
	
	private Void createConfigs() {
		for (int i = 0; i < 10; i++) {
			createConfiguration(i, 0);
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
			}
		}
		try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
		}
		for (int i = 0; i < 10; i++) {
			createConfiguration(i, 5);
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
			}
		}
		try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
		}
		configs.forEach(c->{
			try {
				c.delete();
			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		configs.clear();
		return null;
	}
	
	private void createConfiguration(int increment, int offset) {
		Configuration configuration;
		try {
			configuration = configAdmin.getFactoryConfiguration("MyAlbumConfig", "album-" + increment, "?");
			Dictionary<String, Object> props = new Hashtable<>();
			props.put("name", "name-" + increment);
			props.put("album", "album-" + increment + offset);
			props.put("year", 2000 + increment + offset);
			configuration.updateIfDifferent(props);
			configs.add(configuration);
		} catch (IOException e) {
			System.out.println("Error creating configuration: " + e.getMessage());
		}
	}
	
}
