package com.example.oasipserver.services;


import com.example.oasipserver.dtos.EventDTO;
import com.example.oasipserver.entities.Event;
import com.example.oasipserver.repositories.EventRepository;
import com.example.oasipserver.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public List<EventDTO> getAllEvents (){
        List<Event> eventList = repository.findAll(Sort.by("EventStartTime").descending());

        return listMapper.mapList(eventList, EventDTO.class, modelMapper);
    }

    public EventDTO getEventDetail(Integer bookingId){
        Event event = repository.findById(bookingId).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Event id "+ bookingId + " does not exist!!"));
        return modelMapper.map(event , EventDTO.class);
    }
    public Event save(EventDTO newEvent){
        List<Event> e1 = repository.findAll();
        Event e2 = modelMapper.map(newEvent, Event.class);
        ZonedDateTime oldTimeStart;
        ZonedDateTime oldTimeEnd;
        ZonedDateTime newTimeStart = newEvent.getEventStartTime();
        ZonedDateTime newTimeEnd = newEvent.getEventStartTime().plusMinutes(newEvent.getEventDuration());
        for(int i = 0; i < e1.size() ; i++) {
            if(e1.get(i).getEventCategory().getId() == e2.getEventCategory().getId()) {
                oldTimeStart = e1.get(i).getEventStartTime();
                oldTimeEnd = e1.get(i).getEventStartTime().plusMinutes(e1.get(i).getEventDuration());
                if (overlapTime(oldTimeStart, oldTimeEnd, newTimeStart, newTimeEnd)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This start time is overlap other event!!!");
                }
            }
        }
        return repository.saveAndFlush(e2);
    }
    public boolean overlapTime(ZonedDateTime date_Start1 ,ZonedDateTime date_End1,ZonedDateTime date_Start2, ZonedDateTime date_End2) {
        return date_Start1.isBefore(date_End2) && date_Start2.isBefore(date_End1);
    }
    public Event updateEvent(Event updateEvent, Integer bookingId) {
        Event event = repository.findById(bookingId).map(existEvent -> mapEvent(existEvent, updateEvent)).orElseGet(()->
        {
            updateEvent.setId(bookingId);
            return updateEvent;
        });
        List<Event> e1 = repository.findAll();
        ZonedDateTime oldTimeStart;
        ZonedDateTime oldTimeEnd;
        ZonedDateTime newTimeStart = event.getEventStartTime();
        ZonedDateTime newTimeEnd = event.getEventStartTime().plusMinutes(event.getEventDuration());
        for(int i = 0; i < e1.size() ; i++) {
            if(e1.get(i).getEventCategory().getId() == event.getEventCategory().getId() && e1.get(i).getId() != event.getId()) {
                oldTimeStart = e1.get(i).getEventStartTime();
                oldTimeEnd = e1.get(i).getEventStartTime().plusMinutes(e1.get(i).getEventDuration());
                if (overlapTime(oldTimeStart, oldTimeEnd, newTimeStart, newTimeEnd)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "This start time is overlap other event!!!");
                }
            }
        }
        return repository.saveAndFlush(event);
    }
    private Event mapEvent(Event existEvent, Event updateEvent) {
        if(updateEvent.getEventStartTime() != null) {
            existEvent.setEventStartTime(updateEvent.getEventStartTime());
        }
        if(updateEvent.getEventNotes() != null) {
            existEvent.setEventNotes(updateEvent.getEventNotes());
        }else existEvent.setEventNotes(null);
        return existEvent;
    }

    public void deleteEvent(Integer bookingId){
        repository.findById(bookingId).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND , "This id " + bookingId+ " does not exist!!"));
        repository.deleteById(bookingId);
    }

    public List<EventDTO> getUpcomingEvents (){
        ZonedDateTime now = ZonedDateTime.now();
        List<Event> eventList = repository.findAllByEventStartTimeGreaterThanEqual(now);
        eventList.sort((o1,o2) -> o1.getEventStartTime().compareTo(o2.getEventStartTime()));
        return listMapper.mapList(eventList, EventDTO.class, modelMapper);
    }

    public List<EventDTO> getPastEvents(){
        ZonedDateTime now = ZonedDateTime.now();
        List<Event> eventList = repository.findAllByEventStartTimeIsBefore(now);
        eventList.sort((o1,o2) -> o2.getEventStartTime().compareTo(o1.getEventStartTime()));
        return  listMapper.mapList(eventList , EventDTO.class , modelMapper);
    }
    public List<EventDTO> getEventByDate(String date){
     List<Event> events = repository.findEventByDate(date);
     events.sort(((o1, o2) -> o1.getEventStartTime().compareTo(o2.getEventStartTime())));
     return listMapper.mapList(events , EventDTO.class , modelMapper);
    }


}

