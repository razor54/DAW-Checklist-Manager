package group1.spring_server.domain.resource;

import group1.spring_server.control.ServiceController;
import group1.spring_server.exceptions.MyException;
import org.springframework.hateoas.ResourceSupport;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class HomePageResource extends ResourceSupport {

    public HomePageResource() throws MyException, IOException {

        add(linkTo(methodOn(ServiceController.class)
                .getCheckLists(null))
                .withRel("checklists"));

        add(linkTo(methodOn(ServiceController.class)
                .getTemplates(null))
                .withRel("templates"));
    }
}
