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
package org.gecko.playground.tasks.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.gecko.playground.tasks.Task;
import org.gecko.playground.tasks.TaskInfo;
import org.gecko.playground.tasks.TaskService;
import org.gecko.playground.tasks.TaskState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;
import org.osgi.util.promise.Promise;

/**
 * Test with global bundle context
 * 
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class TaskTestCtxGlobal {

	private BundleContext ctx;

	@BeforeEach
	public void before(@InjectBundleContext BundleContext ctx) {
		this.ctx = ctx;
	}
	
	@AfterEach
	public void after() {
		
	}

	@Test
	@DisplayName("Register Task")
	@Order(1)
	public void testRegister(@InjectService ServiceAware<TaskService> taskServiceAware, @InjectService(cardinality = 0, timeout = 100) ServiceAware<Task> taskAware) throws InterruptedException {
		// is our service there?
		assertNotNull(taskAware);
		TaskService ts = taskServiceAware.getService();
		System.out.println("Task Service Instance " + ts + " - Bundle Context " + ctx);
		
		// no tasks registered
		assertEquals(0, taskAware.getTrackingCount());
		assertTrue(ts.getTaskIds().isEmpty());
		ServiceRegistration<Task> taskReg = ctx.registerService(Task.class, new TestTask("foo", "Foo Task"), null);

		// wait a while, to let DS inject our task into the TaskService
		Thread.sleep(100);
		
		// check Task Info
		assertEquals(1, taskAware.getTrackingCount());
		TaskInfo ti = ts.getTaskInfo("foo");
		assertNotNull(ti);
		assertEquals("Foo Task", ti.getName());
		assertEquals(TaskState.ACTIVE, ti.getState());
		taskReg.unregister();
		// wait a while to let DS remove our Task
		Thread.sleep(100);
		
		// Task list is not empty
		assertFalse(ts.getTaskIds().isEmpty());
		assertEquals(1, ts.getTaskIds().size());
		
		// But the Task is gone
		assertEquals(null, taskAware.getService());
		// We have two changes, service come and service go
		assertEquals(2, taskAware.getTrackingCount());
		
		// Check the TaskInfo proxy element, it must be in REMOVED state
		ti = ts.getTaskInfo("foo");
		assertNotNull(ti);
		assertEquals("Foo Task", ti.getName());
		assertEquals(TaskState.REMOVED, ti.getState());
		
	}
	
	@Test
	@DisplayName("Start/Stop Task")
	@Order(2)
	public void testStop(@InjectService ServiceAware<TaskService> taskServiceAware, @InjectService(cardinality = 0, timeout = 100) ServiceAware<Task> taskAware) throws InterruptedException, InvocationTargetException {
		// is our service there?
		assertNotNull(taskAware);
		TaskService ts = taskServiceAware.getService();
		System.out.println("Task Service Instance " + ts + " - Bundle Context " + ctx);
		
		// one task still registered
		assertEquals(0, taskAware.getTrackingCount());
		assertEquals(1, ts.getTaskIds().size());
		ServiceRegistration<Task> taskReg = ctx.registerService(Task.class, new TestTask("bar", "Bar Task"), null);
		
		// wait a while, to let DS inject our task into the TaskService
		Thread.sleep(100);
		
		// check Task Info
		assertEquals(1, taskAware.getTrackingCount());
		assertEquals(2, ts.getTaskIds().size());
		TaskInfo ti = ts.getTaskInfo("bar");
		assertNotNull(ti);
		assertEquals("Bar Task", ti.getName());
		assertEquals(TaskState.ACTIVE, ti.getState());
		
		Promise<TaskInfo> activateTask = ts.activateTask("bar");
		ti = activateTask.getValue();
		assertEquals("Bar Task", ti.getName());
		assertEquals(TaskState.ACTIVE, ti.getState());
		
		Promise<TaskInfo> deactivateTask = ts.deactivateTask("bar");
		ti = deactivateTask.getValue();
		assertEquals("Bar Task", ti.getName());
		assertEquals(TaskState.INACTIVE, ti.getState());
		
		taskReg.unregister();
		// wait a while to let DS remove our Task
		Thread.sleep(100);
		
		// Task list is not empty
		assertFalse(ts.getTaskIds().isEmpty());
		assertEquals(2, ts.getTaskIds().size());
		// But the Task is gone
		assertEquals(null, taskAware.getService());
		// We have two changes, service come and service go
		assertEquals(2, taskAware.getTrackingCount());
		
		// Check the TaskInfo proxy element, it must be in REMOVED state
		ti = ts.getTaskInfo("foo");
		assertNotNull(ti);
		assertEquals("Foo Task", ti.getName());
		assertEquals(TaskState.REMOVED, ti.getState());
		ti = ts.getTaskInfo("bar");
		assertNotNull(ti);
		assertEquals("Bar Task", ti.getName());
		assertEquals(TaskState.REMOVED, ti.getState());
		
	}

}
