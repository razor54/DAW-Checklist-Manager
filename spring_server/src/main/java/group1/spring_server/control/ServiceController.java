package group1.spring_server.control;


import group1.spring_server.domain.Checklist;
import group1.spring_server.domain.ChecklistItem;
import group1.spring_server.domain.User;
import group1.spring_server.exceptions.*;
import group1.spring_server.service.ChecklistItemService;
import group1.spring_server.service.ChecklistService;
import group1.spring_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private HttpServletResponse res;
    private int itemId;

    @GetMapping("/checklists")
    public Iterable<Checklist> getCheckLists(HttpServletResponse res)  {

        return null;

        /*try {

        }catch (NoSuchUserException e){
            res.sendError(e.error(),e.getMessage());
            return null;
        }*/


    }

    @GetMapping("/checklist/{listId}")
    public Checklist getCheckList(HttpServletResponse res, @PathVariable("listId") int listId) throws IOException {

        try {
            return checklistService.getChecklist(listId);

        }catch (NoSuchChecklistException e){
            res.sendError(e.error(),e.getMessage());
            return null;
        }
    }

    @GetMapping("/checklist/item/{itemId}")
    public ChecklistItem getChecklistItem(HttpServletResponse res, @PathVariable("itemId") int itemId) throws IOException {

        try {
            return checklistItemService.getChecklistItem(itemId);

        }catch (NoSuchChecklistItemException e){
            res.sendError(e.error(),e.getMessage());
            return null;
        }
    }

    @PostMapping("/users")
    public User addUser(HttpServletResponse res, @RequestBody User user) throws IOException {

        try {
            return userService.addUser(user);

        } catch (FailedAddUserException e) {
            res.sendError(e.error(),e.getMessage());
            return null;
        }


    }


    @PostMapping("/checklist")
    public Checklist addChecklist(HttpServletResponse res, @RequestBody Checklist checklist) throws IOException {

        try {
            return checklistService.addChecklist(checklist);

        } catch (FailedAddChecklistException e) {
            res.sendError(e.error(),e.getMessage());
            return null;
        }


    }

    @PostMapping("/checklist/item")
    public ChecklistItem addChecklistItem(HttpServletResponse res, @RequestBody ChecklistItem checklistItem) throws IOException {

        try {
            return checklistItemService.addCheckListItem(checklistItem);

        } catch (FailedAddCheklistItemException e) {
            res.sendError(e.error(),e.getMessage());
            return null;
        }


    }

}
