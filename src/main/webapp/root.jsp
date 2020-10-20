<%-- 
    Document   : root
    Created on : Sep 20, 2020, 5:05:10 PM
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
            String headerFile = request.getAttribute("authType") == "AUTH" ? "auth_header.jsp" : request.getAttribute("authType") == "NO_AUTH" ? "not_auth_header.jsp" : "";
            pageContext.include("components/headers/" + headerFile);
        %>
        <title>${title}</title>
    </head>
    <body>
        <%@include file="components/heading.jsp" %>
        <%
            pageContext.include((String)request.getAttribute("file"));
        %>
    </body>
</html>
