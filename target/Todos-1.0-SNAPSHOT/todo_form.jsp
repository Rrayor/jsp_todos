<%-- 
    Document   : todo_form
    Created on : Sep 19, 2020, 9:47:01 PM
    Author     : simon
--%>

<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<%@page import="com.benjamin_simon.todos.models.Todo"%>
<%  
    boolean createMode = request.getAttribute("todo") == null;
    Todo todo = (Todo)request.getAttribute("todo");
%>

<h1><%= request.getAttribute("todo") == null ? "Create" : "Edit" %> Todo</h1>

<form action="<%= createMode ? Routes.CREATE_TODO : Routes.UPDATE_TODO + "?todo=" + todo.getId() %>" method="post">
    <label for="content">Content:</label>
    <input type="text" name="content" placeholder="Content..." value="${todo.getContent()}"/>
    <small>${messages.content}</small>
    <label for="done">Done:</label>
    <input type="checkbox" name="done" ${todo.done ? "checked" : ""} />
    <small>${messages.done}</small>
    <button type="submit">Save</button>
</form>
