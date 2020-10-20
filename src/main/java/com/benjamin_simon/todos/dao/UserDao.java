/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.dao;

import com.benjamin_simon.todos.models.User;
import com.benjaminsimon.todos.DbConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author simon
 */
public class UserDao {
    
    public User insertUser(User user) throws SQLException {
        try {
            String sql = "INSERT INTO users(id, username, password) VALUES(?, ?, ?) RETURNING id, username, password";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, user.getId());
            st.setString(2, user.getUsername());
            st.setString(3, user.getPassword());
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new User(rs.getString("id"), rs.getString("username"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public User queryUserById(String id, boolean includePassword) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE id=?";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, id);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return includePassword ?
                        new User(rs.getString("id"), rs.getString("username"), rs.getString("password")) :
                        new User(rs.getString("id"), rs.getString("username"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public User queryUserById(String id) throws SQLException {
        return queryUserById(id, false);
    }
    
    public User updateUser(User user) throws SQLException {
        try {
            String sql = "UPDATE users('username', 'password') VALUES(?, ?) WHERE id=?";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getId());
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new User(rs.getString("id"), rs.getString("username"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public User queryUserByUsername(String username, boolean includePassword) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE username=?";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, username);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return includePassword ?
                        new User(rs.getString("id"), rs.getString("username"), rs.getString("password")) :
                        new User(rs.getString("id"), rs.getString("username"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public User queryUserByUsername(String username) throws SQLException {
        return queryUserByUsername(username, false);
    }
    
}
