/*******************************************************************************
 * Copyright (c) 2010 Oracle.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution. 
 * The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at 
 *     http://www.opensource.org/licenses/apache2.0.php.
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *     mkeith - Gemini JPA sample 
 ******************************************************************************/
package org.gecko.playground.jpa;

import java.util.Date;
import java.util.List;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.dynamic.DynamicTypeBuilder;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.gecko.playground.jpa.classloader.OSGiJPADynamicHelper;
import org.gecko.playground.jpa.entities.Account;
import org.gecko.playground.jpa.entities.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

/**
 * Gemini JPA sample client class
 * 
 * @author mkeith
 */
public class AccountClient {
    
    public void run(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Customer c = new Customer("Chan", "Jackie", "1034 KingFu Lane, Los Angeles, CA");
        em.persist(c);
        Account a = new Account(c);
        a.setBalance(100.0);
        em.persist(a);

        em.getTransaction().commit();

        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a", Account.class);
        List<Account> results = q.getResultList();
        System.out.println("\n*** Account Report ***");
        for (Account acct : results) {
            System.out.println("Account: " + acct);
        }
        em.close();
    }
    @SuppressWarnings("unchecked")
	public void runDynamic(EntityManagerFactory emf) {
    	EntityManager em = emf.createEntityManager();
    	JPADynamicHelper helper = new OSGiJPADynamicHelper(em);
    	DynamicClassLoader dcl = helper.getDynamicClassLoader();
    	Class<?> employeeClass= dcl.createDynamicClass("scott.Emp");
    	Class<?> deptClass= dcl.createDynamicClass("scott.Dept");
    	DynamicTypeBuilder employeeBuilder = new DynamicTypeBuilder(employeeClass,
    	    null /*no parent type*/, "EMP");
    	DynamicTypeBuilder deptBuilder = new DynamicTypeBuilder(deptClass,
    	    null /*no parent type*/, "DEPT");
    	employeeBuilder.setPrimaryKeyFields("EMPNO");
    	employeeBuilder.addDirectMapping("employeeNumber", int.class, "EMPNO");
    	employeeBuilder.addDirectMapping("name", String.class, "ENAME");
    	employeeBuilder.addDirectMapping("job", String.class, "JOB");
    	employeeBuilder.addDirectMapping("hiredOn", Date.class, "HIREDATE");
    	employeeBuilder.addDirectMapping("salaryPerWeek", int.class, "SAL");
    	employeeBuilder.addDirectMapping("commision", int.class, "COMM");
    	employeeBuilder.addOneToOneMapping("manager", employeeBuilder.getType(), "MGR");
    	deptBuilder.setPrimaryKeyFields("DEPTNO");
    	deptBuilder.addDirectMapping("deptNumber", int.class, "DEPTNO");
    	deptBuilder.addDirectMapping("name", String.class, "DNAME");
    	deptBuilder.addDirectMapping("location", String.class, "LOC");
    	employeeBuilder.addOneToOneMapping("department", deptBuilder.getType(), "DEPTNO");
    	
    	
    	helper.addTypes(true /*create tables*/, true /*create constraints*/,
    	    employeeBuilder.getType(), deptBuilder.getType());
    	 
    	DynamicEntity deptEntity = deptBuilder.getType().newDynamicEntity();
    	deptEntity.set("deptNumber", 12);
    	deptEntity.set("name", "digitalization");
    	deptEntity.set("location", "Jena DIMC Office");
    	
    	DynamicEntity employeeEntity = employeeBuilder.getType().newDynamicEntity();
    	employeeEntity.set("employeeNumber", 1);
    	employeeEntity.set("name", "Mark");
    	employeeEntity.set("job", "CTO");
//    	employeeEntity.set("hiredOn", new Date());
    	employeeEntity.set("salaryPerWeek", 1750);
    	employeeEntity.set("commision", 42);
    	employeeEntity.set("department", deptEntity);
    	
    	ReadAllQuery query = helper.newReadAllQuery("Emp");
    	List<DynamicEntity> employees = (List<DynamicEntity>)helper.getSession().executeQuery(query);
    	if (employees.isEmpty()) {
    		em.getTransaction().begin();
    		em.persist(deptEntity);
    		em.persist(employeeEntity);
    		em.getTransaction().commit();
    	}
    	
    	//query.addAscendingOrdering("employeeNumber");
    	query.addAscendingOrdering("name");
    	System.out.println("Employees-Size " + employees.size());
    	employees.forEach(e->{
    		System.out.println("Employee " + e.get("name"));
    		DynamicEntity dept = e.get("department");
    		System.out.println("Employee Department " + dept.get("name"));
    	
    	});
    	em.close();
    }
}