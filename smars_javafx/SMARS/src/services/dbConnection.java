package services;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
private static Connection dbconn;
	
	private final static String host = "localhost:3306";
	private final static String dbName = "smars";
	private final static String username = "root";
	private final static String password = "0303";
	 public static Connection getConnection() {
		 if(dbconn==null) {
			 try {
				 Class.forName("com.mysql.cj.jdbc.Driver");
				 dbconn=DriverManager.getConnection("jdbc:mysql://" + host+ "/" + 
						 dbName, username, password);
			 } catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			return dbconn;
				 
			 }
	
}
