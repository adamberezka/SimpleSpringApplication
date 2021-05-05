package pl.adamb.springapps.simplespringapp.service;

import pl.adamb.springapps.simplespringapp.entity.Event;
import pl.adamb.springapps.simplespringapp.entity.VotingOption;

import java.util.Set;

public interface EventService {

    Set<Event> findAll();

    Event findEventById(Integer id);

    Event save(Event event);

    void deleteById(Integer id);

    VotingOption save(VotingOption vo);

    void deleteVOById(Integer id);
}
