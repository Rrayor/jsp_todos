<%@page import="com.benjamin_simon.todos.routing.Routes"%>
<%           
    if(session.getAttribute("userId") != null) {
        response.sendRedirect(Routes.TODOS);
    }
%>

<h1>Welcome</h1>

<a href="login.jsp">Login</a>
<a href="register.jsp">Register</a>

