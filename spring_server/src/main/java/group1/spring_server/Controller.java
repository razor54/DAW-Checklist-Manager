package group1.spring_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/example")
public class Controller {


    @GetMapping("hello")
    public String get() {
        return "<html>" +
                "<img src=\"http://i0.kym-cdn.com/photos/images/original/000/330/442/c01.jpg\" >"+
                "</html>";
    }


}
