package pl.adamb.springapps.simplespringapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamb.springapps.simplespringapp.entity.Event;
import pl.adamb.springapps.simplespringapp.entity.VotingOption;
import pl.adamb.springapps.simplespringapp.repository.EventRepository;
import pl.adamb.springapps.simplespringapp.repository.VotingOptionRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final VotingOptionRepository votingOptionRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, VotingOptionRepository votingOptionRepository) {
        this.eventRepository = eventRepository;
        this.votingOptionRepository = votingOptionRepository;
    }


    @Override
    public Set<Event> findAll() {
        return new HashSet<Event>(eventRepository.findAll());
    }

    @Override
    public Event findEventById(Integer id) {
        return eventRepository.findEventById(id);
    }

    @Override
    public Event save(Event event) {

        return eventRepository.save(event);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public VotingOption save(VotingOption vo) {
        return votingOptionRepository.save(vo);
    }

    @Override
    public void deleteVOById(Integer id) {
        votingOptionRepository.deleteById(id);
    }


}
