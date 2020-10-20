package com.benjamin_simon.todos.controllers.todo;

import com.benjamin_simon.todos.models.Todo;
import com.benjamin_simon.todos.routing.Routes;
import com.benjamin_simon.todos.services.TodoService;
import com.benjamin_simon.todos.util.Utils;
import com.benjamin_simon.todos.validators.TodoValidators;
import com.benjamin_simon.todos.validators.UserValidators;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "delete-todo", urlPatterns = {"/" + Routes.DELETE_TODO})
public class DeleteTodo extends HttpServlet {
    
    private Map<String, String> validateInputs(String todoId, String userId) {
        Map<String, String> messages = new HashMap<>();
        
        //Check todoId
        String todoIdError = TodoValidators.validateUUID(todoId);
        if(!todoIdError.isEmpty()) messages.put("todo", todoIdError);
        
        //Check userId
        String userIdError = UserValidators.validateUUID(userId);
        if(!userIdError.isEmpty()) messages.put("user", userIdError);
        
        return messages;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }
    
    
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
            
        String todoId = request.getParameter("todo");
        String userId = (String)session.getAttribute("userId");

        Map<String, String> messages = validateInputs(todoId, userId);
        request.setAttribute("messages", messages);

        final boolean inputErrors = !messages.isEmpty();
        
        //Todo id or user id is not a valid UUID
        //Redirects to todos instead of error message
        //Could be changed if needed
        if(inputErrors) {
            response.sendRedirect(Routes.TODOS);
            return;
        }
        
        try {
            Todo todo = TodoService.deleteTodoById(todoId, userId);

            //Todo could not be found
            //Redirects to todos instead of error message
            //Could be changed if needed
            if(todo == null) {
                messages.put("todo", "Todo could not be found");
                response.sendRedirect(Routes.TODOS);
                return;
            }
            
            //Everything OK
            //Todo deleted
            //Redirect to TODOS
            response.sendRedirect(Routes.TODOS);
        } catch (IOException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
