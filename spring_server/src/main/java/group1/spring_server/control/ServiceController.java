package group1.spring_server.control;


import group1.spring_server.domain.*;

import group1.spring_server.domain.model.*;
import group1.spring_server.domain.resource.ChecklistItemResource;
import group1.spring_server.domain.resource.ChecklistResource;
import group1.spring_server.domain.resource.UserResource;
import group1.spring_server.exceptions.*;
import group1.spring_server.service.*;
import group1.spring_server.util.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("listing")
public class ServiceController {


    //TODO see if refactor can do the job with constructor
    //TODO POST NEW Template
    //TODO Create the checklist and its items based on template
    //TODO Read a Template


    @Autowired
    private UserService userService;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ChecklistItemService checklistItemService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateItemService templateItemService;

    @GetMapping("/users")
    public ResourceSupport getUsers(AuthCredentials authCredentials) throws UnauthorizedException {
        //TODO TEST ONLY
        String userId = authCredentials.getSessionCode();

        Set<UserResource> collect = StreamSupport
                .stream(userService.getUsers().spliterator(), false)
                .map(user -> {
                    try {
                        return new UserResource(user);
                    } catch (MyException e) {
                        e.printStackTrace();
                        return null;
                    }

                }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getUsers(authCredentials)).withSelfRel();

        return new Resources<UserResource>(collect, link);


    }

    @GetMapping("/user/{id}")
    public ResourceSupport getUser(String id, AuthCredentials authCredentials) throws MyException {
        String userId = authCredentials.getSessionCode();
        if (userId.compareTo(id) != 0) throw new ForbiddenException();

        return new UserResource(userService.getUser(id));

    }

    @GetMapping("/checklists")
    @RequiresAuthentication
    public ResourceSupport getCheckLists(AuthCredentials authCredentials) throws MyException {

        String sessionCode = authCredentials.getSessionCode();

        User user = userService.getUser(sessionCode);


        Set<ChecklistResource> collect = user.getChecklists().stream()
                .map(list -> {
                    try {
                        return new ChecklistResource(list);
                    } catch (MyException e) {
                        e.printStackTrace();
                        return null;
                    }

                }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getCheckLists(authCredentials)).withSelfRel();

        return new Resources<ChecklistResource>(collect, link);

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}")
    public ResourceSupport getCheckList(@PathVariable("listId") int listId, AuthCredentials authCredentials) throws MyException {

        return new ChecklistResource(checklistService.getChecklist(
                listId,
                authCredentials.getSessionCode()
        ));

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}/items")
    public ResourceSupport getCheckListItems(@PathVariable("listId") int listId, AuthCredentials authCredentials) throws MyException {

        Iterable<ChecklistItem> checklistItems = checklistItemService.getChecklistItems(
                listId,
                authCredentials.getSessionCode()
        );

        Set<ChecklistItemResource> collect = StreamSupport.stream(checklistItems.spliterator(), false)
                .map(item -> {
                    try {
                        return new ChecklistItemResource(item);
                    } catch (MyException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getCheckListItems(listId, authCredentials)).withSelfRel();

        Resources<ChecklistItemResource> resources =
                new Resources<ChecklistItemResource>(collect, link);

        return resources;

    }

    @GetMapping("/checklist/item/{itemId}")
    public ResourceSupport getChecklistItem(@PathVariable("itemId") int itemId, AuthCredentials authCredentials) throws MyException {

        return new ChecklistItemResource(
                checklistItemService.getChecklistItem(
                        itemId,
                        authCredentials.getSessionCode()
                ));

    }

    //Maybe receive session code differently

    @PostMapping("/checklist")
    public ResourceSupport addChecklist(@RequestBody Checklist checklist, AuthCredentials authCredentials) throws MyException {

        String sessionCode = authCredentials.getSessionCode();

        checklist.setUser_id(sessionCode);

        return new ChecklistResource(
                checklistService.addChecklist(checklist)
        );

    }

    @PostMapping("/checklist/item")
    public ResourceSupport addChecklistItem(@RequestBody ChecklistItem checklistItem, AuthCredentials authCredentials) throws MyException {

        Checklist ch = checklistService.getChecklist(
                checklistItem.getlist_id(),
                authCredentials.getSessionCode()
        );

        return new ChecklistItemResource(
                checklistItemService.addCheckListItem(checklistItem)
        );

    }

    @PostMapping("/template")
    public Template addTemplate(@RequestBody Template template, AuthCredentials authCredentials) throws MyException {


        template.setUser_id(authCredentials.getSessionCode());

        return templateService.addTemplate(template);

    }

    @PostMapping("/template/item")
    public TemplateItem addTemplateItem(@RequestBody TemplateItem templateItem, AuthCredentials authCredentials) throws MyException {

        //Throws exception if no user is logged or if no list exists with specified id
        Template ch = templateService.getTemplate(
                templateItem.getTemplate_id(),
                authCredentials.getSessionCode()
        );

        return templateItemService.addTemplateItem(templateItem);

    }


    @PostMapping("checklist/template/{templateID}")
    public Checklist addListFromTemplate(@PathVariable("templateID") int templateId, @RequestParam("listName") String listName, AuthCredentials authCredentials) throws IOException, MyException {
        Template template = templateService.getTemplate(templateId, authCredentials.getSessionCode());
        //TODO if template is null throw some exception
        return templateService.useTemplate(template, listName);

    }
}
