/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjamin_simon.todos.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author simon
 */
public class Utils {

    public static void dispatch(String path, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, res);
    }
    
    public static void view(String title, String file, String authType, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setAttribute("title", title);
            request.setAttribute("file", file + ".jsp");
            request.setAttribute("authType", authType);
            dispatch("root.jsp", request, response);
    }
    
    public class AuthTypes {
        public static final String AUTH = "AUTH";
        public static final String NO_AUTH = "NO_AUTH";
        public static final String ALL = "ALL";
    }
}
