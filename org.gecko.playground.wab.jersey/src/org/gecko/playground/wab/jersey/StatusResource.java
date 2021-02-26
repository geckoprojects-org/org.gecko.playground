/*
 * Copyright (c) 2010, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.gecko.playground.wab.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("status")
public class StatusResource {

    @GET
    @Produces("text/plain")
    public String getStatus() {
        return "active";
    }
    
    @GET
    @Produces("application/json")
    public String getStatusJ() {
    	return "{ \"status\": \"active\" }";
    }
}