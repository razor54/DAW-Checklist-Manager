package group1.spring_server.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

@Entity
@Table(name = "checklist", schema = "public")
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private int id;

    @NotNull
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date completionDate;

    @JsonIgnore
    @NotNull
    private String user_id;


    //TODO LAZY fetch--->Mandatory
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private Set<ChecklistItem> items;


    @Transient
    static final DateFormat df = new SimpleDateFormat("uuuu-MM-dd HH:mm:ss.SSS");

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Transient
    public String getDateCompletionFormatted() {
        return completionDate != null ? df.format(completionDate) : null;
    }


    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
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


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Set<ChecklistItem> getItems() {
        return items;
    }

    public void setItems(Set<ChecklistItem> items) {
        this.items = items;
    }
}
