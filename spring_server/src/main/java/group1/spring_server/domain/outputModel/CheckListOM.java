package group1.spring_server.domain.outputModel;

import group1.spring_server.domain.Checklist;

import java.sql.Date;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckListOM {

    private int id;


    private String name;

    private Date completionDate;

    private int user_id;

    private Set<ChecklistItemOM> items;

    public CheckListOM(Checklist checklist) {

        this.id = checklist.getId();
        this.name = checklist.getName();
        this.completionDate = checklist.getCompletionDate();
        this.user_id = checklist.getUser().getId();

        this.items = checklist.getItems()
                .stream()
                .map(ChecklistItemOM::new)
                .collect(Collectors.toSet());
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Set<ChecklistItemOM> getItems() {
        return items;
    }

    public void setItems(Set<ChecklistItemOM> items) {
        this.items = items;
    }
}
