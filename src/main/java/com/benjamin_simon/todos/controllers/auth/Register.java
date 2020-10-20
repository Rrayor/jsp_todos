package com.benjamin_simon.todos.controllers.auth;

import com.benjamin_simon.todos.validators.UserValidators;
import com.benjamin_simon.todos.models.User;
import com.benjamin_simon.todos.routing.Routes;
import com.benjamin_simon.todos.services.UserService;
import com.benjamin_simon.todos.util.Utils;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author simon
 */
@WebServlet(name = "register", urlPatterns = {"/" + Routes.REGISTER})
public class Register extends HttpServlet {
    
    private static final String VIEW = "register";
    
    private static Map<String, String> validateInputs(String username, String password) {
            
            Map<String, String> messages = new HashMap<>();
            
            //Check username
            String userNameError = UserValidators.validateUsername(username);
            if(!userNameError.isEmpty())
                messages.put("username", userNameError);
            
            //Checlk password
            String passwordError = UserValidators.validatePassword(password);
            if(!passwordError.isEmpty())
                messages.put("password", passwordError);
            
            return messages;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, String> messages = validateInputs(username, password);
        request.setAttribute("messages", messages);

        final boolean inputErrors = !messages.isEmpty();

        //Error in username or password
        if(inputErrors) {
            Utils.view("Register", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
            return;
        }

        try {
            
            User usr = UserService.queryUserByUsername(username);

            //Username taken
            if(usr != null) {
                messages.put("username", "A user with that username already exists");
                Utils.view("Register", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
                return;
            }
            
            //Create id and hash password
            //Register
            String id = UUID.randomUUID().toString();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

            usr = UserService.insertUser(id, username, hashed);

            //Insertion didn't work
            if(usr == null) {
                messages.put("server", "Internal Server Error");
                Utils.view("Register", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
                return;
            }
            
            //Everything OK
            //Redirect to LOGIN
            response.sendRedirect(Routes.LOGIN);
            
        } catch (IOException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
