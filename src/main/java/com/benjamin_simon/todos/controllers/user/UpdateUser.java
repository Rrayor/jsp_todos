package com.benjamin_simon.todos.controllers.user;

import com.benjamin_simon.todos.models.User;
import com.benjamin_simon.todos.routing.Routes;
import com.benjamin_simon.todos.services.UserService;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author simon
 */
@WebServlet(name = "update-user", urlPatterns = {"/" + Routes.UPDATE_USER})
public class UpdateUser extends HttpServlet {
    
    private static final String VIEW = "user_form";
    
    private static Map<String, String> validateInputs(String userId, String username, String password, String newPassword) {
            
            Map<String, String> messages = new HashMap<>();
            
            //Check userId
            String userIdError = UserValidators.validateUUID(userId);
            if(!userIdError.isEmpty()) messages.put("user", userIdError);
            
            //Check username
            String userNameError = UserValidators.validateUsername(username);
            if(!userNameError.isEmpty())
                messages.put("username", userNameError);
            
            //Check password
            String passwordError = UserValidators.validatePassword(password);
            if(!passwordError.isEmpty())
                messages.put("password", passwordError);
            
            //Check newPassword
            String newPasswordError = "";
            if(newPassword != null)
                newPasswordError = UserValidators.validatePassword(newPassword);
            
            if(!newPasswordError.isEmpty())
                messages.put("newPassword", newPasswordError);
            
            return messages;
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String id = (String)session.getAttribute("userId");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");

        Map<String, String> messages = validateInputs(id, username, password, newPassword);
        request.setAttribute("messages", messages);

        final boolean inputErrors = !messages.isEmpty();

        //Error in username or password newPassword
        if(inputErrors) {
            Utils.dispatch(VIEW, request, response);
            return;
        }
        
        try {
            User user = UserService.queryUserById(id, true);
            
            //User could not be found
            if(user == null) {
                messages.put("user", "User not found");
                Utils.view("Update User", VIEW, Utils.AuthTypes.AUTH, request, response);
                return;
            }
            
            //Alias for password to handle password change if needed
            String hash = user.getPassword();
            
            final boolean passwordMatch = BCrypt.checkpw(password, hash);
                
            //Password does not match with hash in db
            if(!passwordMatch) {
                Utils.view("Update User", VIEW, Utils.AuthTypes.AUTH, request, response);
                return;
            }
            
            //If password is changed
            if(newPassword != null)
                hash = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            user = UserService.updateUser(id, username, hash);

            //Update did not work 
            if(user == null) {
                messages.put("server", "Internal Server Error");
                Utils.view("Update User", VIEW, Utils.AuthTypes.AUTH, request, response);
                return;
            }
            
            //Everything OK
            //Redirect to TODOS
            //Should be changed if implemented
            response.sendRedirect(Routes.TODOS);
        } catch (IOException | SQLException e) {
            request.setAttribute("exception", e);
            Utils.dispatch(Routes.ERROR, request, response);
        }
    }
}
