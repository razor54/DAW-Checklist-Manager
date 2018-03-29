package group1.spring_server.domain;



import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name= "user", schema = "public")
public class User {

    @Id
    private String id;

    private String name;


    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Checklist> checklists;

    //TODO add list of Templates

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(Set<Checklist> checklists) {
        this.checklists = checklists;
    }
}
