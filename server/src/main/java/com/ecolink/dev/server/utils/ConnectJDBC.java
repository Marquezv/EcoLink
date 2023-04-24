package com.ecolink.dev.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBC {

	public static Connection connectDB() {
		System.out.println("Trying connect to SQLite...");
		Connection conn = null;
		try {
			String sqlUrl = "jdbc:sqlite:sqlite/db/ecolink.db";
			conn = DriverManager.getConnection(sqlUrl);
			System.out.println(conn.isClosed());
			System.out.println("Connected with SQLite.");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}
