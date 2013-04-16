/*
 * Title : Database Handler
 * Description : Creates and handles connections to the database
 * Author : Glen Holmes
 * Date :05 March 2013  
 */

package com.mc_project;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MyDatabaseHandler {
	Connection conn;

	// Creates databaseConnection
	public MyDatabaseHandler() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/surgery_data");
			conn = ds.getConnection();
		} catch (Exception e) {
			Logger log = new Logger();
			log.logCreator("Database connection fault");
		}
	}

	public Connection getConn() {
		Logger log = new Logger();
		log.logCreator("Database Connected");
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
