package group1.spring_server.control;


import group1.spring_server.OutputModel;
import group1.spring_server.domain.*;

import group1.spring_server.exceptions.*;
import group1.spring_server.service.*;
import group1.spring_server.util.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public Iterable<User> getUsers() {
        //TODO TEST ONLY
        return StreamSupport
                .stream(userService.getUsers().spliterator(), false)
                .collect(Collectors.toList());


    }

    @GetMapping("/checklists")
    @RequiresAuthentication
    public Iterable<Checklist> getCheckLists(HttpServletResponse res, AuthCredentials authCredentials) throws IOException {

        try {


            String sessionCode = authCredentials.getSessionCode();
            User user = userService.getUser(sessionCode);

            return user.getChecklists();

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}")
    public OutputModel getCheckList(HttpServletResponse res, @PathVariable("listId") int listId, AuthCredentials authCredentials) throws IOException {

        try {
            return checklistService.getChecklist(
                    listId,
                    authCredentials.getSessionCode()
            );

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/checklist/item/{itemId}")
    public ChecklistItem getChecklistItem(HttpServletResponse res, @PathVariable("itemId") int itemId, AuthCredentials authCredentials) throws IOException {

        try {
            return checklistItemService.getChecklistItem(
                    itemId,
                    authCredentials.getSessionCode()
            );

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }


    @PostMapping("/checklist")
    public Checklist addChecklist(HttpServletResponse res, @RequestBody Checklist checklist, AuthCredentials authCredentials) throws IOException {

        try {

            String sessionCode = authCredentials.getSessionCode();

            checklist.setUser_id(sessionCode);

            return checklistService.addChecklist(checklist);

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/checklist/item")
    public ChecklistItem addChecklistItem(HttpServletResponse res, @RequestBody ChecklistItem checklistItem, AuthCredentials authCredentials) throws IOException {

        try {

            Checklist ch = checklistService.getChecklist(
                    checklistItem.getlist_id(),
                    authCredentials.getSessionCode()
            );

            return checklistItemService.addCheckListItem(checklistItem);

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/template")
    public Template addTemplate(HttpServletResponse res, @RequestBody Template template, AuthCredentials authCredentials) throws IOException {


        try {

            template.setUser_id(authCredentials.getSessionCode());

            return templateService.addTemplate(template);

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/template/item")
    public TemplateItem addTemplateItem(HttpServletResponse res, @RequestBody TemplateItem templateItem, AuthCredentials authCredentials) throws IOException {

        try {

            Template ch = templateService.getTemplate(
                    templateItem.getTemplate_id(),
                    authCredentials.getSessionCode()
            );

            return templateItemService.addTemplateItem(templateItem);

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }


    @PostMapping("checklist/template/{templateID}")
    public Checklist addListFromTemplate(HttpServletResponse res,@PathVariable("templateID")int templateId, @RequestParam("listName") String listName, AuthCredentials authCredentials) throws IOException {
        try {
            Template template = templateService.getTemplate(templateId,authCredentials.getSessionCode());
            //TODO if template is null throw some exception
            return templateService.useTemplate(template,listName);

        }catch (MyException e){
            res.sendError(e.error(),e.getMessage());
            return null;
        }

    }

}
