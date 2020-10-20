<%-- 
    Document   : index.jsp
    Created on : Sep 20, 2020, 5:24:37 PM
    Author     : simon
--%>

<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            response.sendRedirect(Routes.HOME);
        %>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
