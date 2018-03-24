package group1.spring_server.control;

import group1.spring_server.dal.Repository;
import group1.spring_server.view.LoginTemplate;
import group1.spring_server.view.RegisterTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Hashtable;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    //Storage where will be saved the cookie's of the following users that access and login in the api
    private static final Hashtable<Integer,String> cookieStorage = new Hashtable<>();
    private static final String cookieName = "dawserver_logincookie";

    @GetMapping("/register")
    public String getRegister(HttpServletResponse res, @RequestParam("user") String user, @RequestParam("pass") String passoword) {

        try{


            addUser(user,passoword);

            res.sendRedirect("login");

        }catch (Exception e){

        }
    }

    @GetMapping("/login")
    public String get(HttpServletResponse res, @RequestParam("user") String username, @RequestParam("pass") String passoword) {

        try{

            int user = getUser(username,passoword);


            String value = getCookie(user);

            //this method only adds the cookie if does not exist's same header name
            res.addCookie(new Cookie(cookieName,value));

        }catch (Exception e){

        }

    }


    public String getCookie(int user){


        if(cookieStorage.containsKey(user)) return cookieStorage.get(user);

        //generate random value for cookie
        String value = Long.toHexString(Double.doubleToLongBits(Math.random()));

        return cookieStorage.putIfAbsent(user,value);

    }
}
