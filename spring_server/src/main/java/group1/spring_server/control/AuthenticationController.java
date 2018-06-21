package group1.spring_server.control;


import group1.spring_server.domain.AuthCredentials;
import group1.spring_server.domain.model.Checklist;
import group1.spring_server.domain.model.User;
import group1.spring_server.domain.resource.ChecklistResource;
import group1.spring_server.domain.resource.UserResource;
import group1.spring_server.exceptions.MyException;
import group1.spring_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/auth")
public class AuthenticationController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResource getRegister(HttpServletResponse res, @RequestParam("username") String username, @RequestParam("password") String password) throws MyException {


        User user = new User();

        String sessionId = Base64Utils.encodeToString((username + ":" + password).getBytes());

        user.setId(sessionId);
        user.setName(username);

        return new UserResource(userService.addUser(user));
    }


    @PostMapping("/login")
    public UserResource getLogin(HttpServletResponse res, @RequestParam("username") String username, @RequestParam("password") String password) throws MyException {

        String sessionId = Base64Utils.encodeToString((username + ":" + password).getBytes());

        User user = userService.getUser(sessionId);

        return new UserResource(user);
    }


}
