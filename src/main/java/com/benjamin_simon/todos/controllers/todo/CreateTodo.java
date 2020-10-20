package com.benjamin_simon.todos.controllers.todo;

import com.benjamin_simon.todos.validators.TodoValidators;
import com.benjamin_simon.todos.models.Todo;
import com.benjamin_simon.todos.routing.Routes;
import com.benjamin_simon.todos.services.TodoService;
import com.benjamin_simon.todos.util.Utils;
import com.benjamin_simon.todos.validators.UserValidators;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
@WebServlet(name = "create-todo", urlPatterns = {"/" + Routes.CREATE_TODO})
public class CreateTodo extends HttpServlet {
    
    private static final String VIEW = "todo_form";
    
    private Map<String, String> validateInputs(String content, String userId) {
        Map<String, String> messages = new HashMap<>();
        
        //Check content
        String contentError = TodoValidators.validateContent(content);
        if(!contentError.isEmpty()) messages.put("content", contentError);
        
        //Check userId
        String userIdError = UserValidators.validateUUID(userId);
        if(!userIdError.isEmpty()) messages.put("user", userIdError);
        
        return messages;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
            
        String content = request.getParameter("content");
        boolean done = "on".equals(request.getParameter("done"));
        
        Map<String, String> messages = validateInputs(content, userId);
        request.setAttribute("messages", messages);

        final boolean inputErrors = !messages.isEmpty();
        
        //Error in content
        if(inputErrors) {
            Utils.view("Create Todo", VIEW, Utils.AuthTypes.AUTH, request, response);
            return;
        }
        
        try {
            //Create id and set userId
            //Insert Todo
            String id = UUID.randomUUID().toString();

            Todo todo = TodoService.insertTodo(id, content, done, userId);

            //Insertion didn't work
            if(todo == null) {
                messages.put("server", "Internal Server Error");
                Utils.view("Create Todo", VIEW, Utils.AuthTypes.AUTH, request, response);
                return;
            }
            
            //Everything OK
            //Redirect to TODOS
            response.sendRedirect(Routes.TODOS);
        } catch (IOException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
