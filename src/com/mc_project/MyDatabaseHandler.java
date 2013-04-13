/*
 * Title : Database Handler
 * Description : Creates and handles connections to the database
 * Author : Glen Holmes
 * Date :05 March 2013  
 */

package com.mc_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabaseHandler {

	private Statement statement;
	// private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	Connection connection = null;

	// Creates databaseConnection
	public MyDatabaseHandler() {
		System.out
				.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/surgery", "root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	// Used for Queries expecting results
	// Select Statements
	// e.g. ResultSet rs = dbHandler.retQuery("select * from table");
	public ResultSet retQuery(String query) throws SQLException {
		ResultSet rs = statement.executeQuery(query);
		return rs;
	}

	// Used for Queries just carrying out query
	// Insert, Delete, etc...
	public void nonRetQuery(String query) throws SQLException {
		statement.executeQuery(query);
	}

}
