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

    /**
     * User checklists
     *
     * @param res             http response
     * @param authCredentials refers to the fact that authentication is required
     * @return User checkLists
     * @throws IOException
     */

    @GetMapping("/checklists")
    @RequiresAuthentication
    public Iterable<Checklist> getCheckLists(HttpServletResponse res, AuthCredentials authCredentials) throws IOException {


        //TODO Verify if Authorization token is present

        if (authCredentials == null) {
            // send error 402 not authorized
        }

        String sessionCode = authCredentials.getSessionCode();

        try {
            User user = userService.getUser(sessionCode);

            return user.getChecklists();

        } catch (NoSuchUserException e) {
            res.sendError(e.error(), e.getMessage());
        }


        return null;

    }

    @RequiresAuthentication
    @GetMapping("/checklist/{listId}")
    public Checklist getCheckList(HttpServletResponse res, @PathVariable("listId") int listId, AuthCredentials authCredentials) throws IOException {

        try {
            //TODO
            if (authCredentials == null) {
                //do something
                return null;
            }

            Checklist checklist = checklistService.getChecklist(listId);

            if (checklist.getUser_id().compareTo(authCredentials.getSessionCode()) != 0) {
                //send 403 forbidden
                return null;
            }

            return checklist;

        } catch (NoSuchChecklistException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/checklist/item/{itemId}")
    public ChecklistItem getChecklistItem(HttpServletResponse res, @PathVariable("itemId") int itemId, AuthCredentials authCredentials) throws IOException {

        try {
            //TODO
            if (authCredentials == null) {
                //do something
                return null;
            }

            ChecklistItem checklistItem = checklistItemService.getChecklistItem(itemId);

            int listId = checklistItem.getlist_id();

            Checklist checklist = checklistService.getChecklist(listId);

            if(checklist.getUser_id().compareTo(authCredentials.getSessionCode())!=0){
                // res.sendError(403) access denied
                return null;
            }

            return checklistItem;

        } catch (NoSuchChecklistItemException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
            // TODO maybe just catch (NoSuchChecklistItemException|NoSuchChecklistException e)
            //and catch both exceptions treating them equally??
        } catch (NoSuchChecklistException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/users")
    public User addUser(HttpServletResponse res, @RequestBody User user) throws IOException {

        try {
            return userService.addUser(user);

        } catch (FailedAddUserException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }


    }


    @PostMapping("/checklist")
    public Checklist addChecklist(HttpServletResponse res, @RequestBody Checklist checklist, AuthCredentials authCredentials) throws IOException {

        try {

            String sessionCode = authCredentials.getSessionCode();
            if (sessionCode == null){
                //402 not authorized
                return null;
            }
            checklist.setUser_id(sessionCode);
            return checklistService.addChecklist(checklist);

        } catch (FailedAddChecklistException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        }


    }

    @PostMapping("/checklist/item")
    public ChecklistItem addChecklistItem(HttpServletResponse res, @RequestBody ChecklistItem checklistItem,AuthCredentials authCredentials) throws IOException {

        try {
            if(authCredentials.getSessionCode()==null){
                return null;
            }

            Checklist ch = checklistService.getChecklist( checklistItem.getlist_id() );

            if(ch.getUser_id().compareTo(authCredentials.getSessionCode())!=0){

                //403 forbidden access to that list
                return null;

            }

            return checklistItemService.addCheckListItem(checklistItem);

        } catch (FailedAddCheklistItemException e) {
            res.sendError(e.error(), e.getMessage());
            return null;
        } catch (NoSuchChecklistException e) {
            //Not a valid list id
            e.printStackTrace();
            return null;
        }


    }

    //TODO POST NEW Template
    //TODO Create the checklist and its items based on template
    //TODO Read a Template

}
