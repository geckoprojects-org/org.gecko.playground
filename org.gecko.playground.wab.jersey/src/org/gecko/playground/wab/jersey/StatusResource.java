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

import java.util.Collections;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.gecko.playground.jaxb.Address;
import org.gecko.playground.jaxb.Person;

@Path("status")
public class StatusResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
        return "active";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatusJ() {
    	return "{ \"status\": \"active\" }";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getStatusXML() {
    	Person result = new Person();
    	result.setFirstName("Emil");
    	result.setLastName("Tester");
    	result.setId("test");
    	Address a = new Address();
    	a.setStreet("Teststreet");
    	a.setCity("Jena");
    	a.setZIP("07745");
    	result.setAddress(Collections.singletonList(a));
    	return Response.ok(result).build();
    }
}