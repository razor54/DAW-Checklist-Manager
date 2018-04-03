package group1.spring_server.control;


import group1.spring_server.domain.*;

import group1.spring_server.domain.model.*;
import group1.spring_server.domain.resource.*;
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

        Iterable<User> users = userService.getUsers();

        Iterable<UserResource> collect = () -> StreamSupport
                .stream(users.spliterator(), false)
                .map(user -> {
                    try {
                        return new UserResource(user);
                    } catch (MyException e) {
                        e.printStackTrace();
                        return null;
                    }

                }).iterator();

        Link link = linkTo(methodOn(ServiceController.class)
                .getUsers(authCredentials)).withSelfRel();

        return new Resources<UserResource>(collect, link);


    }

    @GetMapping("/user/{id}")
    public ResourceSupport getUser(@PathVariable("id") String id, AuthCredentials authCredentials) throws MyException {
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

        Link user_link = linkTo(methodOn(ServiceController.class)
                .getUser(user.getId(), null)).withSelfRel();

        return new Resources<ChecklistResource>(collect, link, user_link);

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

        return new Resources<>(collect, link);

    }

    @GetMapping("/checklist/item/{itemId}")
    public ResourceSupport getChecklistItem(@PathVariable("itemId") int itemId, AuthCredentials authCredentials) throws MyException {

        return new ChecklistItemResource(
                checklistItemService.getChecklistItem(
                        itemId,
                        authCredentials.getSessionCode()
                ));

    }

    @GetMapping("/templates")
    public ResourceSupport getTemplates(AuthCredentials authCredentials) throws MyException {
        String sessionCode = authCredentials.getSessionCode();

        Set<TemplateResource> collect = userService.getUser(sessionCode).getTemplates().stream()
                .map(template -> {
                    try {
                        return new TemplateResource(template);

                    } catch (MyException e) {
                        return null;
                    }
                }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getTemplates(null))
                .withSelfRel();
        Link user_link = linkTo(methodOn(ServiceController.class)
                .getUser(sessionCode, null))
                .withSelfRel();

        return new Resources<>(collect, link, user_link);
    }

    @GetMapping("/template/{id}")
    public ResourceSupport getTemplate(@PathVariable("id") int id, AuthCredentials authCredentials) throws MyException {
        String sessionCode = authCredentials.getSessionCode();

        return new TemplateResource(templateService.getTemplate(id, sessionCode));
    }

    @GetMapping("/template/item/{itemId}")
    public ResourceSupport getTemplateItem(@PathVariable("itemId") int itemId, AuthCredentials authCredentials) throws MyException {

        return new TemplateItemResource(
                templateItemService.getTemplateItem(
                        itemId,
                        authCredentials.getSessionCode()));
    }

    @GetMapping("/template/{templateId}/items")
    public ResourceSupport getTemplateItems(@PathVariable("templateId") int templateId, AuthCredentials authCredentials) throws MyException {
        Iterable<TemplateItem> itemsForTemplate = templateItemService.getItemsforTemplate(
                templateId,
                authCredentials.getSessionCode());

        Set<TemplateItemResource> collect = StreamSupport.stream(itemsForTemplate.spliterator(), false
        ).map(item -> {
            try {
                return new TemplateItemResource(item);
            } catch (MyException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getTemplateItems(templateId, null))
                .withSelfRel();

        Link parentLink = linkTo(methodOn(ServiceController.class)
                .getTemplate(templateId, null))
                .withRel("parent");

        return new Resources<>(collect, link, parentLink);
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
    public ResourceSupport addTemplate(@RequestBody Template template, AuthCredentials authCredentials) throws MyException {


        template.setUser_id(authCredentials.getSessionCode());

        return new TemplateResource(templateService.addTemplate(template));

    }

    @PostMapping("/template/item")
    public ResourceSupport addTemplateItem(@RequestBody TemplateItem templateItem, AuthCredentials authCredentials) throws MyException {

        //Throws exception if no user is logged or if no list exists with specified id
        Template ch = templateService.getTemplate(
                templateItem.getTemplate_id(),
                authCredentials.getSessionCode()
        );

        return new TemplateItemResource(templateItemService.addTemplateItem(templateItem));

    }


    @PostMapping("checklist/template/{templateID}")
    public ResourceSupport addListFromTemplate(@PathVariable("templateID") int templateId, @RequestParam("listName") String listName, AuthCredentials authCredentials) throws IOException, MyException {
        Template template = templateService.getTemplate(templateId, authCredentials.getSessionCode());
        //TODO if template is null throw some exception
        return new ChecklistResource(templateService.useTemplate(template, listName));

    }
}
