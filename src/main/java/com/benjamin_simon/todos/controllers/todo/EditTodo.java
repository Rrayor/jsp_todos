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
@WebServlet(name = "edit-todo", urlPatterns = {"/" + Routes.EDIT_TODO})
public class EditTodo extends HttpServlet {

    private static final String VIEW = "todo_form";
    
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String todoId = request.getParameter("todo");
        String userId = (String)session.getAttribute("userId");
        
        Map<String, String> messages = validateInputs(todoId, userId);

        final boolean noInputErrors = messages.isEmpty();
        
        Todo todo = null;
        
        try {
            //There is a valid todoId
            //Query by todoId
            if(noInputErrors)
                todo = TodoService.queryTodoById(todoId, userId);
            
            //The view receives the todo from db or null
            request.setAttribute("todo", todo);
            Utils.view("Edit Todo", VIEW, Utils.AuthTypes.AUTH, request, response);
        } catch (IOException | ServletException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }

}
