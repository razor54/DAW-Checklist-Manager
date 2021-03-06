package group1.spring_server.domain.resource;

import group1.spring_server.control.ServiceController;
import group1.spring_server.domain.model.TemplateItem;
import group1.spring_server.exceptions.MyException;
import org.springframework.hateoas.ResourceSupport;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TemplateItemResource extends ResourceSupport {

    private final TemplateItem templateItem;

    public TemplateItemResource(final TemplateItem templateItem) throws MyException, IOException {
        this.templateItem = templateItem;

        add(linkTo(methodOn(ServiceController.class)
                .getTemplateItem(templateItem.getId(), null))
                .withSelfRel());

        add(linkTo(methodOn(ServiceController.class)
                .getTemplate(templateItem.getTemplate_id(), null))
                .withRel("parent"));
    }

    public TemplateItem getTemplateItem() {
        return templateItem;
    }
}
