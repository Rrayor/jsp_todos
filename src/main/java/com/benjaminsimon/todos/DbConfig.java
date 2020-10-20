/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjaminsimon.todos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author simon
 */
public class DbConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/Todo?stringtype=unspecified";
    private static final String DB_USERNAME="username";
    private static final String DB_PASSWORD="password";
    
    private final Connection con;
    
    public static final DbConfig instance = new DbConfig();
    
    private DbConfig() {
        this.con = connectToDB();
    }
    
    private Connection connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
            
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public Connection getCon() {
        return this.con;
    }
}
