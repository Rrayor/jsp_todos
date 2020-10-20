/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.validators;

/**
 *
 * @author simon
 */
public class UserValidators extends Validators{
    
    private static final int MAX_USERNAME_LENGTH = 32;
    private static final int MAX_PASSWORD_LENGTH = 64;
 
    public static String validateUsername(String username) {
        
        if(username == null || username.trim().isEmpty())
            return "Please enter a username";
        
        if(username.length() > MAX_USERNAME_LENGTH)
            return "Username can not be longer than 32 characters";
        
        if(!username.matches("\\p{Alnum}+"))
            return "Username must contain alphanumeric characters only";
        
        return "";
    }
    
    public static String validatePassword(String password) {
        
        if(password == null || password.trim().isEmpty())
            return "Please enter a password";
        
        if(password.length() > MAX_PASSWORD_LENGTH)
            return "Invalid password";
        
        return "";
    }
}
