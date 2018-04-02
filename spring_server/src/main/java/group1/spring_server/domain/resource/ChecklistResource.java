package group1.spring_server.domain.resource;


import group1.spring_server.control.ServiceController;
import group1.spring_server.domain.model.Checklist;
import group1.spring_server.exceptions.MyException;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ChecklistResource extends ResourceSupport {


    private final Checklist checklist;

    public ChecklistResource(final Checklist checklist) throws MyException {
        this.checklist = checklist;

        add(linkTo(methodOn(ServiceController.class)
                .getCheckList(checklist.getId(), null))
                .withSelfRel());
        add(linkTo(methodOn(ServiceController.class)
                .getCheckListItems(checklist.getId(),null))
                .withRel("items"));
    }

    public Checklist getChecklist() {
        return checklist;
    }
}
