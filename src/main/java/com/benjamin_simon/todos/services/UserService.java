/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.services;

import com.benjamin_simon.todos.dao.UserDao;
import com.benjamin_simon.todos.models.User;
import java.sql.SQLException;

/**
 *
 * @author simon
 */
public class UserService {
    
    private static final UserDao dao = new UserDao();

    public UserService() {
    }
    
    
    
    public static User insertUser(String id, String username, String password) throws SQLException {
        return dao.insertUser(new User(id, username, password));
    }
    
    public static User updateUser(String id, String username, String password) throws SQLException {
        return dao.updateUser(new User(id, username, password));
    }
    
    public static User queryUserById(String id, boolean includePassword) throws SQLException {
        return dao.queryUserById(id, includePassword);
    }
    
    public static User queryUserById(String id) throws SQLException {
        return dao.queryUserById(id);
    }
    
    public static User queryUserByUsername(String username, boolean includePassword) throws SQLException {
        return dao.queryUserByUsername(username, includePassword);
    }
    
    public static User queryUserByUsername(String username) throws SQLException {
        return dao.queryUserByUsername(username);
    }
}
