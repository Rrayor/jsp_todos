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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author simon
 */
@WebServlet(name = "login", urlPatterns = {"/" + Routes.LOGIN})
public class Login extends HttpServlet {
    
    private static final String VIEW = "login";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Utils.view("Login", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
    
    
    
    private static Map<String, String> validateInputs(String username, String password) {
            
            Map<String, String> messages = new HashMap<>();
            
            //Check username
            String userNameError = UserValidators.validateUsername(username);
            if(!userNameError.isEmpty()) messages.put("username", userNameError);
            
            //Check password
            String passwordError = UserValidators.validatePassword(password);
            if(!passwordError.isEmpty()) messages.put("password", passwordError);
            
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
                Utils.dispatch(VIEW, request, response);
                return;
            }
            
        try {
            User usr = UserService.queryUserByUsername(username, true);

            //User could not be found
            if(usr == null) {
                messages.put("password", "Username or password incorrect");
                Utils.view("Login", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
                return;
            }

            //Password hasn't returned from db.
            //This makes checking it with BCrypt obsolete
            if(usr.getPassword().isEmpty()) {
                messages.put("server", "Internal Server Error");
                Utils.view("Login", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
                return;
            }

            boolean passwordMatch = BCrypt.checkpw(password, usr.getPassword());

            //Password and hash don't match
            if(!passwordMatch) {
                messages.put("password", "Username or password incorrect");
                Utils.view("Login", VIEW, Utils.AuthTypes.NO_AUTH, request, response);
                return;
            }

            //Everything OK
            //Login
            //Redirect to TODOS
            HttpSession session = request.getSession();
            session.setAttribute("userId", usr.getId());

            response.sendRedirect(Routes.TODOS);
        } catch (IOException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
