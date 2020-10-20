<%-- 
    Document   : not_auth_header
    Created on : Sep 20, 2020, 3:54:07 PM
    Author     : simon
--%>

<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1
            response.setHeader("pragma", "no-cache"); //HTTP 1.0
            response.setHeader("Expires", "0"); //Proxies
            
            if(session.getAttribute("userId") != null) {
                response.sendRedirect(Routes.TODOS);
            }
        %>
