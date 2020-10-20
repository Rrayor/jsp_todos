package com.benjamin_simon.todos.controllers.todo;

import com.benjamin_simon.todos.models.Todo;
import com.benjamin_simon.todos.routing.Routes;
import com.benjamin_simon.todos.services.TodoService;
import com.benjamin_simon.todos.util.Utils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simon
 */
@WebServlet(name = "todos", urlPatterns = {"/" + Routes.TODOS})
public class GetTodos extends HttpServlet {

    private static final String VIEW = "todos";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        final String userId = (String)session.getAttribute("userId");
        
        try {
            ArrayList<Todo> todos = TodoService.queryTodoByUser(userId);
            
            //Everything OK
            //View receives todos
            request.setAttribute("todos", todos);
            Utils.view("Todos", VIEW, Utils.AuthTypes.AUTH, request, response);
        } catch (IOException | ServletException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
