package br.com.unesc.shared;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class DataBaseManager {
	private static final String URL = "jdbc:mysql://localhost/mapa?useTimezone=true&serverTimezone=UTC&characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";
	
	public static final ConnectionSource connectionSource() {
		try {
			ConnectionSource connectionSource = new JdbcConnectionSource(URL);
			((JdbcConnectionSource)connectionSource).setUsername(USER);
	    	((JdbcConnectionSource)connectionSource).setPassword(PASSWORD);
	    	
	    	return connectionSource;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
