package pl.adamb.springapps.simplespringapp.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Role> rolesList;

    @ManyToMany
    @JoinTable(name="events_users",
                joinColumns = @JoinColumn(name="users_id"),
                inverseJoinColumns = @JoinColumn(name="event_id"))
    private Set<Event> eventsVoted;

    @OneToMany
    @JoinColumn(name="owner_id")
    private Set<Event> ownedEvents;

    public User() {
    }

    public Set<Event> getEventsVoted() {
        return eventsVoted;
    }

    public void setEventsVoted(Set<Event> eventsVoted) {
        this.eventsVoted = eventsVoted;
    }

    public Set<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(Set<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(Set<Role> roles) {
        this.rolesList = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName) {
        this.username = firstName;
    }

}
