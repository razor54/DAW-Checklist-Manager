package group1.spring_server.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "checklist", schema = "public")
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private int id;

    private String name;

    private Date completionDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private Set<ChecklistItem> items;



    @Transient
    static final DateFormat df = new SimpleDateFormat("uuuu-MM-dd HH:mm:ss.SSS");

    @Transient
    public String getDateCompletionFormatted() {
        return df.format(completionDate);
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



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Set<ChecklistItem> getItems() {
        return items;
    }

    public void setItems(Set<ChecklistItem> items) {
        this.items = items;
    }
}
