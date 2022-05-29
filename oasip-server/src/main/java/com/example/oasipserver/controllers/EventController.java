package com.example.oasipserver.controllers;


import com.example.oasipserver.dtos.EventDTO;
import com.example.oasipserver.entities.Event;
import com.example.oasipserver.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://intproj21.sit.kmutt.ac.th/")
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping("")
    public List<EventDTO> getAllEvent(){
            return service.getAllEvents();
    }

    @GetMapping("/{bookingId}")
    public EventDTO getEventById(@PathVariable Integer bookingId) {
        return service.getEventDetail(bookingId);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@Valid @RequestBody EventDTO newEvent){
         return service.save(newEvent);
    }

    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable Integer bookingId) {
        service.deleteEvent(bookingId);
    }

    @PutMapping("/{bookingId}")
    public Event update(@Valid @RequestBody Event updateEvent, @PathVariable Integer bookingId) {
        return service.updateEvent(updateEvent , bookingId);
    }
    @GetMapping("/upcoming")
    public List<EventDTO> upcomingEvent(){
        return service.getUpcomingEvents();
    }

    @GetMapping("/past")
    public List<EventDTO> pastEvents(){
        return service.getPastEvents();
    }

    @GetMapping("/sort-date/{date}")
    public List<EventDTO> getEventByDate(@PathVariable String date) {
        return service.getEventByDate(date);
    }

}

