package pl.adamb.springapps.simplespringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamb.springapps.simplespringapp.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Event findEventById(Integer id);



}
