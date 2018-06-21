package group1.spring_server.control;


import group1.spring_server.domain.AuthCredentials;
import group1.spring_server.domain.BearerToken;
import group1.spring_server.domain.model.*;
import group1.spring_server.domain.resource.*;
import group1.spring_server.exceptions.ForbiddenException;
import group1.spring_server.exceptions.MyException;
import group1.spring_server.exceptions.UnauthorizedException;
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
@RequestMapping("api/listing")
public class ServiceController {


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

    @GetMapping("/")
    public ResourceSupport getHomePage(BearerToken bearerToken) throws IOException, MyException {

        bearerToken.getSessionCode();

        return new HomePageResource();
    }


    @GetMapping("/user/{id}")
    public ResourceSupport getUser(@PathVariable("id") String id, BearerToken bearerToken) throws MyException {
        String userId = bearerToken.getSessionCode();
        if (userId.compareTo(id) != 0) throw new ForbiddenException();

        return new UserResource(userService.getUser(id));

    }

    @GetMapping("/checklists")
    @RequiresAuthentication
    public Resources<ChecklistResource> getCheckLists(BearerToken bearerToken) throws MyException {

        String sessionCode = bearerToken.getSessionCode();

        User user = userService.getUser(sessionCode);

        Iterable<Checklist> userCheckLists = checklistService.getUserCheckLists(user.getId());

        Set<ChecklistResource> collect = StreamSupport
                .stream(userCheckLists.spliterator(), false)
                .map(list -> {
                    try {
                        return new ChecklistResource(list);
                    } catch (MyException e) {
                        e.printStackTrace();
                        return null;
                    }

                }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getCheckLists(bearerToken)).withSelfRel();

        Link user_link = linkTo(methodOn(ServiceController.class)
                .getUser(user.getId(), null)).withSelfRel();

        return new Resources<>(collect, link, user_link);

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}")
    public ChecklistResource getCheckList(@PathVariable("listId") int listId, BearerToken bearerToken) throws MyException {

        return new ChecklistResource(checklistService.getChecklist(
                listId,
                bearerToken.getSessionCode()
        ));

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}/items")
    public Resources<ChecklistItemResource> getCheckListItems(@PathVariable("listId") int listId, BearerToken bearerToken) throws MyException {

        Iterable<ChecklistItem> checklistItems = checklistItemService.getChecklistItems(
                listId,
                bearerToken.getSessionCode()
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
                .getCheckListItems(listId, bearerToken)).withSelfRel();

        return new Resources<>(collect, link);

    }

    @GetMapping("/checklist/item/{itemId}")
    public ChecklistItemResource getChecklistItem(@PathVariable("itemId") int itemId, BearerToken bearerToken) throws MyException {

        return new ChecklistItemResource(
                checklistItemService.getChecklistItem(
                        itemId,
                        bearerToken.getSessionCode()
                ));

    }

    @GetMapping("/templates")
    public Resources<TemplateResource> getTemplates(BearerToken bearerToken) throws MyException {
        String sessionCode = bearerToken.getSessionCode();

        User user = userService.getUser(sessionCode);

        Iterable<Template> userTemplates = templateService.getUserTemplates(user.getId());

        Set<TemplateResource> collect = StreamSupport
                .stream(userTemplates.spliterator(), false)
                .map(template -> {
                    try {
                        return new TemplateResource(template);

                    } catch (MyException | IOException e) {
                        return null;
                    }
                }).collect(Collectors.toSet());

        Link link = linkTo(methodOn(ServiceController.class)
                .getTemplates(null))
                .withSelfRel();
        Link user_link = linkTo(methodOn(ServiceController.class)
                .getUser(sessionCode, null))
                .withRel("user");

        return new Resources<>(collect, link, user_link);
    }

    @GetMapping("/template/{id}")
    public TemplateResource getTemplate(@PathVariable("id") int id, BearerToken bearerToken) throws MyException, IOException {
        String sessionCode = bearerToken.getSessionCode();

        return new TemplateResource(templateService.getTemplate(id, sessionCode));
    }

    @GetMapping("/template/item/{itemId}")
    public TemplateItemResource getTemplateItem(@PathVariable("itemId") int itemId, BearerToken bearerToken) throws MyException, IOException {

        return new TemplateItemResource(
                templateItemService.getTemplateItem(
                        itemId,
                        bearerToken.getSessionCode()));
    }

    @GetMapping("/template/{templateId}/items")
    public Resources<TemplateItemResource> getTemplateItems(@PathVariable("templateId") int templateId, BearerToken bearerToken) throws MyException, IOException {

        Iterable<TemplateItem> itemsForTemplate = templateItemService.getItemsforTemplate(
                templateId,
                bearerToken.getSessionCode());

        Set<TemplateItemResource> collect = StreamSupport.stream(itemsForTemplate.spliterator(), false
        ).map(item -> {
            try {
                return new TemplateItemResource(item);
            } catch (MyException | IOException e) {
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
    public ChecklistResource addChecklist(@RequestBody Checklist checklist, BearerToken bearerToken) throws MyException {

        String sessionCode = bearerToken.getSessionCode();

        checklist.setUser_id(sessionCode);

        return new ChecklistResource(
                checklistService.addChecklist(checklist)
        );

    }

    @PostMapping("/checklist/item")
    public ChecklistItemResource addChecklistItem(@RequestBody ChecklistItem checklistItem, BearerToken bearerToken) throws MyException {

        Checklist ch = checklistService.getChecklist(
                checklistItem.getlist_id(),
                bearerToken.getSessionCode()
        );

        return new ChecklistItemResource(
                checklistItemService.addCheckListItem(checklistItem)
        );

    }

    @PostMapping("/template")
    public TemplateResource addTemplate(@RequestBody Template template, BearerToken bearerToken) throws MyException, IOException {


        template.setUser_id(bearerToken.getSessionCode());

        return new TemplateResource(templateService.addTemplate(template));

    }

    @PostMapping("/template/item")
    public TemplateItemResource addTemplateItem(@RequestBody TemplateItem templateItem, BearerToken bearerToken) throws MyException, IOException {

        //Throws exception if no user is logged or if no list exists with specified id
        Template ch = templateService.getTemplate(
                templateItem.getTemplate_id(),
                bearerToken.getSessionCode()
        );

        return new TemplateItemResource(templateItemService.addTemplateItem(templateItem));

    }


    @PostMapping("checklist/template/{templateID}")
    public ChecklistResource addListFromTemplate(@PathVariable("templateID") int templateId, @RequestParam("listName") String listName, BearerToken bearerToken) throws MyException {
        Template template = templateService.getTemplate(templateId, bearerToken.getSessionCode());
        //TODO if template is null throw some exception
        return new ChecklistResource(templateService.useTemplate(template, listName));

    }


    @PutMapping("/checklist/item")
    public ChecklistItemResource updateChecklistItem(@RequestBody ChecklistItem checklistItem, BearerToken bearerToken) throws MyException {

        Checklist ch = checklistService.getChecklist(
                checklistItem.getlist_id(),
                bearerToken.getSessionCode()
        );




        return new ChecklistItemResource(
                checklistItemService.updateChecklistItem(checklistItem.getId(),checklistItem)
        );

    }
    @PutMapping("/checklist/")
    public ChecklistResource updateChecklist(@RequestBody Checklist checklist, BearerToken bearerToken) throws MyException {

        return new ChecklistResource(
                checklistService.updateChecklist(checklist)
        );

    }
    @PutMapping("/template/item")
    public TemplateItemResource updateTemplateItem(@RequestBody TemplateItem templateItem, BearerToken bearerToken) throws MyException, IOException {

        Template ch = templateService.getTemplate(
                templateItem.getTemplate_id(),
                bearerToken.getSessionCode()
        );




        return new TemplateItemResource(
                templateItemService.updateTemplateItem(templateItem)
        );

    }

    @PutMapping("/template/")
    public TemplateResource updateTemplate(@RequestBody Template checklist, BearerToken bearerToken) throws MyException, IOException {

        return new TemplateResource(
                templateService.updateTemplate(checklist)
        );

    }
}
