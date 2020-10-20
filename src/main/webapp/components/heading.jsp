<%-- 
    Document   : heading
    Created on : Sep 20, 2020, 4:04:30 PM
    Author     : simon
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<nav class="horizontal-nav">
    <h1>TODO-APP</h1>
    
    <%
        boolean loggedIn = (String)session.getAttribute("userId") != null;
        String loggedInValue = loggedIn + "";
    %>
    
    <c:set var="auth" value="<%= loggedInValue %>" />
    <c:if test="${auth}">
        <form action="<%= Routes.LOGOUT %>" method="post">
            <button type="submit">Logout</button>
        </form>
    </c:if>
</nav>
