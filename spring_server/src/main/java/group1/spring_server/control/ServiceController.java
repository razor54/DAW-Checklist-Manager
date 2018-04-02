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
    public Iterable<Checklist> getCheckLists(HttpServletResponse res, AuthCredentials authCredentials) throws MyException {

        String sessionCode = authCredentials.getSessionCode();
        User user = userService.getUser(sessionCode);

        return user.getChecklists();

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}")
    public OutputModel getCheckList(HttpServletResponse res, @PathVariable("listId") int listId, AuthCredentials authCredentials) throws MyException {

        return checklistService.getChecklist(
                listId,
                authCredentials.getSessionCode()
        );

    }

    @GetMapping("/checklist/item/{itemId}")
    public ChecklistItem getChecklistItem(HttpServletResponse res, @PathVariable("itemId") int itemId, AuthCredentials authCredentials) throws MyException {

        return checklistItemService.getChecklistItem(
                itemId,
                authCredentials.getSessionCode()
        );

    }


    @PostMapping("/checklist")
    public Checklist addChecklist(HttpServletResponse res, @RequestBody Checklist checklist, AuthCredentials authCredentials) throws MyException {

        String sessionCode = authCredentials.getSessionCode();

        checklist.setUser_id(sessionCode);

        return checklistService.addChecklist(checklist);

    }

    @PostMapping("/checklist/item")
    public ChecklistItem addChecklistItem(HttpServletResponse res, @RequestBody ChecklistItem checklistItem, AuthCredentials authCredentials) throws MyException {

        Checklist ch = checklistService.getChecklist(
                checklistItem.getlist_id(),
                authCredentials.getSessionCode()
        );

        return checklistItemService.addCheckListItem(checklistItem);

    }

    @PostMapping("/template")
    public Template addTemplate(HttpServletResponse res, @RequestBody Template template, AuthCredentials authCredentials) throws MyException {


        template.setUser_id(authCredentials.getSessionCode());

        return templateService.addTemplate(template);

    }

    @PostMapping("/template/item")
    public TemplateItem addTemplateItem(HttpServletResponse res, @RequestBody TemplateItem templateItem, AuthCredentials authCredentials) throws MyException {

        Template ch = templateService.getTemplate(
                templateItem.getTemplate_id(),
                authCredentials.getSessionCode()
        );

        return templateItemService.addTemplateItem(templateItem);

    }


    @PostMapping("checklist/template/{templateID}")
    public Checklist addListFromTemplate(HttpServletResponse res,@PathVariable("templateID")int templateId, @RequestParam("listName") String listName, AuthCredentials authCredentials) throws IOException, MyException {
        Template template = templateService.getTemplate(templateId,authCredentials.getSessionCode());
        //TODO if template is null throw some exception
        return templateService.useTemplate(template,listName);

    }

}
