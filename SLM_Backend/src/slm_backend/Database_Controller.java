/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slm_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lenovo
 */
public class Database_Controller {
    protected Connection Database_Connector(){
        System.out.println("Database connecting...");
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection =  DriverManager.getConnection("jdbc:mysql://54.169.212.51/ut_db" +
                    "?user=admin&password=admin&characterEncoding=utf-8");
            if(connection != null){
                System.out.println("Database connected");
            }else{
                System.out.println("Database connect fail");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    protected void Database_close(Connection connection){
        try{
            if(connection != null){
                connection.close();
                System.out.println("Database connection close");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    protected void executeQuery(Connection connection, String query){
        ResultSet resultSet = null;
        try{
            Statement statement = connection.createStatement();
            String config = "SET CHARACTER SET UTF8";
            statement.execute(config);
            int count = statement.executeUpdate(query);
            if(count>0){
                System.out.println("Execute query");
            }else{
                System.out.println("No execute query");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
