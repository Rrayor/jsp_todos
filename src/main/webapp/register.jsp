<%-- 
    Document   : register
    Created on : Sep 18, 2020, 9:42:00 PM
    Author     : simon
--%>
<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<h1>Register</h1>

<small>${messages.server}</small>
<small>${messages.success}</small>
<form action="<%= Routes.REGISTER %>" method="post">
    <label for="username">Username:</label>
    <input type="text" name="username" />
    <small>${messages.username}</small>
    <label for="password">Password:</label>
    <input type="password" name="password" />
    <small>${messages.password}</small>
    <button type="submit">Register</button>
</form>
