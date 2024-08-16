package struts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnector {

	
	private static Connection dbconn = null;
	
	public static Connection getConnection() {
		String dbDriver = System.getProperty("DB_DRIVER");
		String dbUrl = System.getProperty("DB_URL");
		String dbUser = System.getProperty("DB_USER");
		String dbPassword = System.getProperty("DB_PASSWORD");
		
		Connection connection = null;
		
		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			dbconn = connection;
			
			System.out.println("DB연결 성공");
			
			return dbconn;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void close() {
		
		if(dbconn == null) {
			return;
		}
		
		try {
			if(!dbconn.isClosed()) {
				System.out.println("DB연결 해제");
				dbconn.close();
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		dbconn = null;
	}
	
	
}
