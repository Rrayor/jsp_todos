<%-- 
    Document   : Error
    Created on : Sep 18, 2020, 5:29:29 PM
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body bgcolor="red">
        <%@include file="components/heading.jsp" %>
        <h1><font color="white">Error</font></h1>
        <section style="background-color: #efefef; padding: 10px;">
            <%= exception != null ? exception.getMessage() : "" %>
        </section>
    </body>
</html>
