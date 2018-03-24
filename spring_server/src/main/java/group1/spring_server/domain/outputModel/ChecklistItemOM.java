package group1.spring_server.domain.outputModel;

import group1.spring_server.domain.ChecklistItem;

import java.sql.Date;

public class ChecklistItemOM {


    private int id;

    private String name;

    private String state;

    private String description;

    private int listId;


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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }



    public ChecklistItemOM(ChecklistItem item) {
        this.id = item.getId();

        this.description = item.getDescription();

        this.name = item.getName();

        this.listId = item.getChecklist().getId();

        this.state = item.getState();

    }

}
