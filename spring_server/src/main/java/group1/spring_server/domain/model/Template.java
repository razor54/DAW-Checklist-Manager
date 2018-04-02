package group1.spring_server.domain.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "template", schema = "public")
public class Template {

    private String user_id;

    private String name;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private int id;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id")
    private Set<TemplateItem> items;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<TemplateItem> getItems() {
        return items;
    }

    public void setItems(Set<TemplateItem> items) {
        this.items = items;
    }
}
