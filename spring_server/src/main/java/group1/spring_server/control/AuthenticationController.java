package group1.spring_server.control;


import group1.spring_server.domain.User;
import group1.spring_server.exceptions.FailedAddUserException;
import group1.spring_server.exceptions.MyException;
import group1.spring_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    //Storage where will be saved the cookie's of the following users that access and login in the api
    private static final Hashtable<Integer, String> cookieStorage = new Hashtable<>();
    private static final String cookieName = "dawserver_logincookie";


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void getRegister(HttpServletResponse res, @RequestParam("username") String username, @RequestParam("password") String password) throws MyException {


        User user = new User();

        String sessionId = Base64Utils.encodeToString((username + ":" + password).getBytes());

        user.setId(sessionId);
        user.setName(username);

        userService.addUser(user);
    }
}
