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
public class TodoValidators extends Validators{
    private static final int MAX_CONTENT_LENGTH = 255;
    
    public static String validateContent(String content) {
        
        if(content == null || content.trim().isEmpty())
            return "Please enter the content";
        
        if(content.length() > MAX_CONTENT_LENGTH)
            return "Content can not be longer than 255 characters";
        
        if(!content.matches("^[a-zA-Z\\s]*$"))
            return "Content must contain alphanumeric characters only";
        
        return "";
    }
}
