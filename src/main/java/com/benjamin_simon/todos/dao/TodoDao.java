/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.dao;

import com.benjamin_simon.todos.models.Todo;
import com.benjaminsimon.todos.DbConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author simon
 */
public class TodoDao {
    
    public Todo insertTodo(Todo todo) throws SQLException {
        try {
            String sql = "INSERT INTO todo(id, content, done, userid) VALUES(?, ?, ?, ?) RETURNING id, content, done, userid";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, todo.getId());
            st.setString(2, todo.getContent());
            st.setBoolean(3, todo.isDone());
            st.setString(4, todo.getUserId());
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new Todo(rs.getString("id"), rs.getString("content"), rs.getBoolean("done"), rs.getString("userid"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public Todo updateTodo(Todo todo) throws SQLException {
        try {
            String sql = "UPDATE todo SET content=?, done=? WHERE todo.id=? RETURNING todo.id, content, done, userid";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, todo.getContent());
            st.setBoolean(2, todo.isDone());
            st.setString(3, todo.getId());
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new Todo(rs.getString("id"), rs.getString("content"), rs.getBoolean("done"), rs.getString("userid"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public Todo queryTodoById(String id, String userid) throws SQLException {
        try {
            String sql = "SELECT * FROM todo WHERE id=? AND userid=?";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, id);
            st.setString(2, userid);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new Todo(rs.getString("id"), rs.getString("content"), rs.getBoolean("done"), rs.getString("userid"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    public ArrayList<Todo> queryTodosByUser(String userid) throws SQLException {
        ArrayList<Todo> todos = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM todo WHERE userid=?";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, userid);
            
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                todos.add(new Todo(rs.getString("id"), rs.getString("content"), rs.getBoolean("done"), rs.getString("userid")));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return todos;
    }
    
    public Todo deleteTodoById(String id, String userid) throws SQLException {
        try {
            String sql = "DELETE FROM todo WHERE id=? AND userid=? RETURNING id, content, done, userid";
            Connection con = DbConfig.instance.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, id);
            st.setString(2, userid);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new Todo(rs.getString("id"), rs.getString("content"), rs.getBoolean("done"), rs.getString("userid"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
        return null;
    }
}
