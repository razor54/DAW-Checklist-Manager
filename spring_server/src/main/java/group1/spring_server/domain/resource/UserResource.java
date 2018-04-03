package group1.spring_server.domain.resource;

import group1.spring_server.control.ServiceController;
import group1.spring_server.domain.model.User;
import group1.spring_server.exceptions.MyException;
import org.springframework.hateoas.ResourceSupport;

import static com.sun.tools.internal.xjc.reader.Ring.add;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends ResourceSupport {

    private final User user;

    public UserResource(final User user) throws MyException {
        this.user = user;

        add(linkTo(methodOn(ServiceController.class)
                .getUser(user.getId(), null))
                .withSelfRel());


        add(linkTo(methodOn(ServiceController.class)
                .getCheckLists(null))
                .withRel("checklists"));

        add(linkTo(methodOn(ServiceController.class)
                .getTemplates(null))
                .withRel("templates"));

    }

    public User getUser() {
        return user;
    }
}
