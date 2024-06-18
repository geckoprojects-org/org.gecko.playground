/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.playground.ds.optional.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.gecko.playground.ds.optional.DataSourceService;
import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author mark
 * @since 12.06.2024
 */
@Component
public class DataSourceImpl implements DataSourceService {
	
	public DataSourceImpl() {
		System.out.println("Create DataSourceImpl");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.playground.ds.dynamics.api.DataSourceService#getDataSource()
	 */
	@Override
	public javax.sql.DataSource getDataSource() {
		return new DataSource() {
			
			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Logger getParentLogger() throws SQLFeatureNotSupportedException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setLoginTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLogWriter(PrintWriter out) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getLoginTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public PrintWriter getLogWriter() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Connection getConnection(String username, String password) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Connection getConnection() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
