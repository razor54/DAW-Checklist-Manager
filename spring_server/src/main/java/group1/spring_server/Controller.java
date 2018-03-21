package group1.spring_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping("/example")
public class Controller {

    private HashMap<String,String> userList = new HashMap<>();


    @GetMapping("/login")
    public String getLogin() {

        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<form action=\"authenticaton\">\n" +
                "  Username:<br>\n" +
                "  <input type=\"text\" name=\"user\">\n" +
                "  <br>\n" +
                "  Password:<br>\n" +
                "  <input type=\"text\" name=\"pass\" >\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form> \n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";

    }


    @GetMapping("/register")
    public String getRegister() {
        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<form action=\"hello\">\n" +
                "  Username:<br>\n" +
                "  <input type=\"text\" name=\"user\">\n" +
                "  <br>\n" +
                "  Password:<br>\n" +
                "  <input type=\"text\" name=\"pass\" >\n" +
                "  <br><br>\n" +
                "  Mail:<br>\n" +
                "  <input type=\"text\" name=\"mail\" >\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form> \n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";


    }


    @GetMapping("/authentication")
    public void post(HttpServletResponse res, @RequestParam("user") String user, @RequestParam("pass") String passoword) throws IOException {

        if(!userList.containsKey(user) && !userList.get(user).equals(passoword)) {
            try {
                res.sendRedirect("register");
            } catch (IOException e) {
                res.sendError(500);
            }
        }

        try {
            res.sendRedirect("hello");
        } catch (IOException e) {
                res.sendError(500);
        }


    }


    @GetMapping("/hello")
    public String getHello() {

        return "<html>" +
                "<img src=\"http://i0.kym-cdn.com/photos/images/original/000/330/442/c01.jpg\" >"+
                "</html>";
    }




}
