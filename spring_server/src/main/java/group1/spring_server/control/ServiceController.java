package group1.spring_server.control;

import group1.spring_server.view.LoginTemplate;
import group1.spring_server.view.RegisterTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class ServiceController {




    @GetMapping("/user/{id}")
    public String getUser() {return "Not Implemented";}


    @GetMapping("/checklist/{id}")
    public String getCheckList() {return "Not Implemented";}


    @GetMapping("/checklistitem/{id}")
    public String getCheckListItem() {return "Not Implemented";}


}
