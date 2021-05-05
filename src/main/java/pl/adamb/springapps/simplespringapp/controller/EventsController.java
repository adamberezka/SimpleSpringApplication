package pl.adamb.springapps.simplespringapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.adamb.springapps.simplespringapp.entity.Event;
import pl.adamb.springapps.simplespringapp.entity.User;
import pl.adamb.springapps.simplespringapp.entity.VotingOption;
import pl.adamb.springapps.simplespringapp.service.EventService;
import pl.adamb.springapps.simplespringapp.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventsController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @RequestMapping("/all")
    public String allEvents(Model model){

        Set<Event> events = eventService.findAll();
        model.addAttribute("events", events);

        return "allEvents";
    }

    @RequestMapping("/event")
    public String eventById(Model model, @RequestParam Integer eventId){

        Event event = eventService.findEventById(eventId);
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user;
            if(!(principal instanceof String)) {
                user = userService.findUserByUsername(((UserDetails) principal).getUsername());
                if (user.isPresent())
                    if (event.getUsers().contains(user.get()))
                        model.addAttribute("voted", "1");
            }
        }

        model.addAttribute("event", event);

        return "event";
    }


    @RequestMapping("/vote")
    public String vote(@RequestParam Integer eventId,
                       @RequestParam Integer votingOptionId,
                       Model model){

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findUserByUsername(principal.getUsername());

        Event event = eventService.findEventById(eventId);

        if(event.getUsers().contains(user.get())){
            model.addAttribute("message", "You have already voted in this event!");
        }else{
            event.getUsers().add(user.get());
            event.getVotingOptionById(votingOptionId).addVote();
            eventService.save(event);
            model.addAttribute("message", "Successfully voted!");
        }

        model.addAttribute("event", event);

        return "voted";
    }

    @RequestMapping("/myEvents")
    public String myEvents(Model model){

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findUserByUsername(principal.getUsername());

        model.addAttribute("events", user.get().getOwnedEvents());

        return "myEvents";
    }

    @RequestMapping("/addEventForm")
    public String addEventForm(Model model){

        Event event = new Event();
        model.addAttribute("newEvent", event);

        return "eventForm";
    }

    @RequestMapping("/addEvent")
    public String addEvent(@ModelAttribute("newEvent") Event event, Model model){

        Event newEvent = eventService.save(event);

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findUserByUsername(principal.getUsername());

        Set<Event> events = user.get().getOwnedEvents();
        if(events == null)
            events = new HashSet<Event>();

        events.add(newEvent);

        userService.save(user.get());

        model.addAttribute("event", newEvent);

        return "editEvent";
    }

    @RequestMapping("/editEvent")
    public String editEvent(@RequestParam Integer eventId, Model model){

        model.addAttribute("event", eventService.findEventById(eventId));

        return "editEvent";
    }

    @RequestMapping("/deleteEvent")
    public String deleteEvent(@RequestParam Integer eventId){

        Event event = eventService.findEventById(eventId);

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user;
            if(!(principal instanceof String)) {
                user = userService.findUserByUsername(((UserDetails) principal).getUsername());
                if (user.isPresent())
                    if(user.get().getOwnedEvents().contains(event)) {
                        eventService.deleteById(eventId);

                    }
            }
        }

        return "redirect:/events/myEvents";
    }

    @RequestMapping("/addVOForm")
    public String addVOForm(Model model, @RequestParam Integer eventId){

        VotingOption vo = new VotingOption();

        model.addAttribute("eventId", eventId);
        model.addAttribute("vo", vo);

        return "voForm";
    }

    @RequestMapping("/addVO")
    public String addVO(@ModelAttribute("vo") VotingOption vo, @RequestParam Integer eventId){

        Event event = eventService.findEventById(eventId);
        vo.setVotes(0);

        Set<VotingOption> vos = event.getVotingOptions();
        if(vos == null) {
            vos = new HashSet<VotingOption>();
            vos.add(vo);
            event.setVotingOptions(vos);
        }else {
            event.getVotingOptions().add(vo);
        }
        vo.setEvent(event);
        eventService.save(vo);
        eventService.save(event);
        return "redirect:/events/editEvent?eventId="+eventId;
    }

    @RequestMapping("/deleteVO")
    public String deleteVO(@RequestParam Integer eventId,
                           @RequestParam Integer voId){

        eventService.deleteVOById(voId);

        return "redirect:/events/editEvent?eventId="+eventId;
    }

}
