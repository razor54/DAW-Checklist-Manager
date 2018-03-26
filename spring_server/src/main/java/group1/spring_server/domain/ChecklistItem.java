package group1.spring_server.domain;

import javax.persistence.*;

@Entity
@Table(name= "item",schema = "public")
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private int id;

    private String name;

    private String state;

    private String description;


    
    private int list_id;



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



    public int getlist_id() {
        return list_id;
    }

    public void setlist_id(int checklist) {
        this.list_id = checklist;
    }


}
