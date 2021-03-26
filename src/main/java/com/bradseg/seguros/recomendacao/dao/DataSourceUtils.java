/**
 *
 * @author renato tomaz nati
 * @version 1.0
 * 
 */
package com.bradseg.seguros.recomendacao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/***
 * 
 * @author renato nati
 * classe de conexao com o banco, no websphere sera substituida pelas padrao
 * @version 1.0
 */

public  class DataSourceUtils {
	private static   Connection connection=null;
	/**
	 * @pre 
	 * Singleton de conexao
	 * @return connexao
	 * @version 1.0
	 */
	public static Connection getConnection() {

		
        
		   String url ="jdbc:sqlserver://127.0.0.1\\MEUSQLSERVER;databaseName=ESTUDO01;user=SA;password=Abc,1234";
	if(connection==null) {
		   try {
			  
			connection = DriverManager.getConnection(
			    		url);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	return connection;
	}
	
	 /*
	   public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
		//   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		   String url ="jdbc:sqlserver://127.0.0.1\\MEUSQLSERVER;databaseName=ESTUDO01;user=SA;password=Abc,1234";
	      
		   Connection conexao = DriverManager.getConnection(
	        		url);
	        System.out.println("Conectado!");
	        
	        conexao.close();
	    }*/
}
