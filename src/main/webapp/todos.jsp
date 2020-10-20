<%-- 
    Document   : todos
    Created on : Sep 19, 2020, 2:06:16 AM
    Author     : simon
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<h1>Todos:</h1>

<a href="<%= Routes.EDIT_TODO %>">Add Todo</a>

<c:forEach  items="${todos}" var="todo">
    <h2>${todo.content}</h2>
    <p>
        <c:if test="${todo.done == true}">
            done
        </c:if>
    </p>
    <a href="<%= Routes.DELETE_TODO %>?todo=${todo.id}">Delete</a>
    <a href="<%= Routes.EDIT_TODO %>?todo=${todo.id}">Edit</a>
</c:forEach>

