package group1.spring_server.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    //TODO LAZY FETCH
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_id",fetch = FetchType.EAGER,orphanRemoval = true)
    //@JoinColumn(name = "user_id")
    private Set<Checklist> checklists;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_id",fetch = FetchType.EAGER,orphanRemoval = true)
    //@JoinColumn(name = "user_id")
    private Set<Template> templates;


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

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }
}
