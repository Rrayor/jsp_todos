/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.validators;

import java.util.regex.Pattern;

/**
 *
 * @author simon
 */
public class Validators {
    
    private static final Pattern UUID_REGEX = Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
    
    public static String validateUUID(String uuid) {
        
        if(uuid == null || !uuid.matches(UUID_REGEX.pattern()))
            return "Internal Server Error";
        
        return "";
    }
}
