package group1.spring_server.domain.outputModel;

import group1.spring_server.domain.Checklist;
import group1.spring_server.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;

public class UserOM {

    private int id;

    private String name;

    private String pass;

    private Set<CheckListOM> checklists;


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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<CheckListOM> getChecklists() {
        return checklists;
    }

    public void setChecklists(Set<CheckListOM> checklists) {
        this.checklists = checklists;
    }

    public UserOM(User user){

        this.id = user.getId();
        this.name = user.getName();
        this.pass = user.getPass();

        this.checklists = user.getChecklists()
                .stream()
                .map(CheckListOM::new)
                .collect(Collectors.toSet());

    }


}
