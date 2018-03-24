package group1.spring_server.control;


import group1.spring_server.domain.Checklist;
import group1.spring_server.domain.ChecklistItem;
import group1.spring_server.domain.User;
import group1.spring_server.repository.CheckListRepository;
import group1.spring_server.service.CheckListItemService;
import group1.spring_server.service.CheckListService;
import group1.spring_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("listing")
public class ChecklistController {


    //TODO see if refactor can do the job with constructor

    @Autowired
    private UserService userService;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private CheckListItemService checkListItemService;


    @GetMapping("/checklist/{listId}")
    public Checklist getCheckList(@PathVariable("listId") int listId, CheckListRepository repository) {


        Checklist list = this.checkListService.getCheckList(listId);

        //TODO error


        return list;
    }


    @GetMapping("/checklists")
    public Iterable<Checklist> getCheckLists() {

        return this.checkListService.getCheckLists();

    }


    @GetMapping("/users")
    public Iterable<User> getCheckList() {

        Iterable<User> lists = userService.getUsers();

        return lists;
    }


    //TODO GET checklists for specific user
    //TODO GET Items for a particular checklist
    //TODO POSTS


    @PostMapping("/users")
    public User addUser(@RequestBody User user) {

        try {

            user = userService.addUser(user);

            return user;

        } catch (Exception e) {

            //TODO
            //Failed to add user
            return null;
        }


    }

}
