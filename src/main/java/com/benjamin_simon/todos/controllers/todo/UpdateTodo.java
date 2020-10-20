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
@WebServlet(name = "update-todo", urlPatterns = {"/" + Routes.UPDATE_TODO})
public class UpdateTodo extends HttpServlet {
    
    private Map<String, String> validateInputs(String todoId, String content, String userId) {
        Map<String, String> messages = new HashMap<>();
        
        //Check todoId
        String todoIdError = TodoValidators.validateUUID(todoId);
        if(!todoIdError.isEmpty()) messages.put("content", todoIdError);
        
        //Check content
        String contentError = TodoValidators.validateContent(content);
        if(!contentError.isEmpty()) messages.put("content", contentError);
        
        //Check userId
        String userIdError = UserValidators.validateUUID(userId);
        if(!userIdError.isEmpty()) messages.put("user", userIdError);
        
        return messages;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
            
        String todoId = request.getParameter("todo");
        String content = request.getParameter("content");
        boolean done = "on".equals(request.getParameter("done"));
        String userId = (String)session.getAttribute("userId");
        
        Map<String, String> messages = validateInputs(todoId, content, userId);
        request.setAttribute("messages", messages);

        final boolean inputErrors = !messages.isEmpty();
        
        //Error in content or todoId
        if(inputErrors) {
            Utils.dispatch(Routes.EDIT_TODO, request, response);
            return;
        }
        
        try {
            Todo todo = TodoService.updateTodo(todoId, content, done, userId);

            //Todo could not be found
            if(todo == null) {
                messages.put("todo", "Todo could not be found");
                Utils.dispatch(Routes.EDIT_TODO, request, response);
                return;
            }
            
            response.sendRedirect(Routes.TODOS);
        } catch (IOException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
