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
package org.geckoprojects.log4j;

import org.osgi.annotation.bundle.Requirement;
import org.osgi.annotation.bundle.Requirements;

/**
 * Annotation to define capabilities an dependencies.
 * This is a convenient annotation that takes care of the logging configuration.
 * @author Mark Hoffmann
 *
 */
@Requirements({
	@Requirement(namespace = LogManagerProvider.CAPABILITY_NAMESPACE, name = LogManagerProvider.LOGGING_CONFIG, version="2.0")
})
public @interface RequireLoggingConfig {

}
