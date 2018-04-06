package group1.spring_server.domain.resource;

import group1.spring_server.control.ServiceController;
import group1.spring_server.domain.model.Template;
import group1.spring_server.exceptions.MyException;
import org.springframework.hateoas.ResourceSupport;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TemplateResource extends ResourceSupport {

    private final Template template;

    public TemplateResource(Template template) throws MyException, IOException {
        this.template = template;

        add(linkTo(methodOn(ServiceController.class)
                .getTemplate(template.getId(), null))
                .withSelfRel());

        add(linkTo(methodOn(ServiceController.class)
                .getTemplateItems(template.getId(), null))
                .withRel("items"));

        add(linkTo(methodOn(ServiceController.class)
                .getUser(template.getUser_id(), null))
                .withRel("user"));


        add(linkTo(methodOn(ServiceController.class)
                .addListFromTemplate(template.getId(), null, null))
                .withRel("checklist_create"));

    }

    public Template getTemplate() {
        return template;
    }
}
