package group1.spring_server.control;


import group1.spring_server.domain.model.User;
import group1.spring_server.domain.resource.UserResource;
import group1.spring_server.exceptions.MyException;
import group1.spring_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
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
}
