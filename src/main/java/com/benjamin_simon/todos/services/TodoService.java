/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.services;

import com.benjamin_simon.todos.dao.TodoDao;
import com.benjamin_simon.todos.models.Todo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author simon
 */
public class TodoService {

    private static final TodoDao dao = new TodoDao();
    
    public TodoService() {
    }
    
    public static Todo insertTodo(String id, String content, boolean done, String userId) throws SQLException {
        return dao.insertTodo(new Todo(id, content, done, userId));
    }
    
    public static Todo updateTodo(String id, String content, boolean done, String userId) throws SQLException{
        return dao.updateTodo(new Todo(id, content, done, userId));
    }
    
    public static Todo queryTodoById(String id, String userId) throws SQLException{
        return dao.queryTodoById(id, userId);
    }
    
    public static ArrayList<Todo> queryTodoByUser(String userId) throws SQLException{
        return dao.queryTodosByUser(userId);
    }
    
    public static Todo deleteTodoById(String id, String userId) throws SQLException{
        return dao.deleteTodoById(id, userId);
    }
}
