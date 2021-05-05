package pl.adamb.springapps.simplespringapp.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToMany
    @JoinTable(name="events_users",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="users_id"))
    private Set<User> users;

    @OneToMany
    @JoinColumn(name="events_id")
    private Set<VotingOption> votingOptions;

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<VotingOption> getVotingOptions() {
        return votingOptions;
    }

    public void setVotingOptions(Set<VotingOption> votingOptions) {
        this.votingOptions = votingOptions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public VotingOption getVotingOptionById(Integer id){

        for(VotingOption votingOption: votingOptions){
            if(votingOption.getId() == id)
                return votingOption;
        }

        return null;
    }
}
