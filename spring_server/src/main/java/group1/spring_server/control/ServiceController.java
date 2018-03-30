package group1.spring_server.control;


import group1.spring_server.domain.AuthCredentials;
import group1.spring_server.domain.Checklist;
import group1.spring_server.domain.ChecklistItem;
import group1.spring_server.domain.User;

import group1.spring_server.exceptions.*;
import group1.spring_server.service.ChecklistItemService;
import group1.spring_server.service.ChecklistService;
import group1.spring_server.service.UserService;
import group1.spring_server.util.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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

        } catch (MyException e ){
            res.sendError(e.error(),e.getMessage());
            return null;
        }

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}")
    public Checklist getCheckList(HttpServletResponse res, @PathVariable("listId") int listId, AuthCredentials authCredentials) throws IOException {

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

    /*
    /* TODO this method is realy necessary ?
    /*
    @PostMapping("/users")
    public User addUser(HttpServletResponse res, @RequestBody User user) throws IOException {

        try {
            return userService.addUser(user);

        } catch (MyException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }
    */




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
    public ChecklistItem addChecklistItem(HttpServletResponse res, @RequestBody ChecklistItem checklistItem,AuthCredentials authCredentials) throws IOException {

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


}
