/**
 * Copyright (c) 2012 - 2022 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.ds.config;

import org.gecko.playground.ds.config.api.Album;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

@RequireConfigurationAdmin
@Component(immediate = true, name = "MyAlbumConfig", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class AlbumComponent implements Album {
	
	private MyFancyConfig c;

	@Activate
	public void activate(MyFancyConfig c) {
		this.c = c;
		System.out.println("Activate " + c.name() + " from " + c.year() + "(" + c.album() + ") from instance " + this);
	}
	
	@Modified
	public void modified(MyFancyConfig c) {
		this.c = c;
		System.out.println("Modified " + c.name() + " from " + c.year() + "(" + c.album() + ") from instance " + this);
		
	}
	
	@Deactivate
	public void deactivate(MyFancyConfig c) {
		System.out.println("De-Activate " + c.name() + " from " + c.year() + "(" + c.album() + ") from instance " + this);
		
	}

	@Override
	public String getAlbum() {
		return c.name() + " - " + c.album() + " (" + c.year() + ")";
	}

}
