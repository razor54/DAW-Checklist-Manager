package group1.spring_server.domain.resource;

import group1.spring_server.control.ServiceController;
import group1.spring_server.domain.model.ChecklistItem;
import group1.spring_server.exceptions.MyException;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ChecklistItemResource extends ResourceSupport {


    private final ChecklistItem checklistItem;

    public ChecklistItemResource(final ChecklistItem checklistItem) throws MyException {
        this.checklistItem = checklistItem;

        add(linkTo(methodOn(ServiceController.class)
                .getChecklistItem(checklistItem.getId(), null))
                .withSelfRel());

        add(linkTo(methodOn(ServiceController.class)
                .getCheckList(checklistItem.getlist_id(),null))
                .withRel("parent"));
    }

    public ChecklistItem getChecklistItem() {
        return checklistItem;
    }
}