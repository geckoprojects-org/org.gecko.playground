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
package org.gecko.playground.jaxb;

import org.osgi.annotation.bundle.Capability;
import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;
/**
 * We define a annotation, that aggregates multiple OSGi annotations, no make life easier.
 * We require certain bundles to be used, but still we don't use Require Bundle. This can be done, if
 * there is a certain framework, with a set of dependencies to be used, e.g. Vaadin, Jetty, Jersey, Netty
 * 
 * In the same way, the annotation provides a capability "jaxb" and we use as name the implementation "sun.xml.bind" and a version
 * 
 * We can later use this capability for resolving
 * @author mark
 * @since 28.01.2022
 */
@Requirements({
	@Requirement(namespace = "osgi.identity", name = "com.sun.xml.bind.jaxb-impl", filter = "(&(version>=2.3.3)(!(version>=3.0)))"),
	@Requirement(namespace = "osgi.identity", name = "org.glassfish.hk2.osgi-resource-locator")
})
@Capability(namespace = "playground.jaxb", name = "sun.xml.bind", version = "2.3")
public @interface RequireJaXB {

}
