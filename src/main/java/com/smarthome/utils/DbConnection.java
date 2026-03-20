package com.smarthome.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/smartHome";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "password";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

	public static Connection initializeDb() throws ClassNotFoundException, SQLException {

		Class.forName(DB_DRIVER);
		Connection con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

		return con;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
