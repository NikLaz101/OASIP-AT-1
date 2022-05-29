package com.example.oasipserver.repositories;

import com.example.oasipserver.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event , Integer> {
    public List<Event> findAllByEventStartTimeGreaterThanEqual(ZonedDateTime now);
    public List<Event> findAllByEventStartTimeIsBefore(ZonedDateTime now);


    @Query(value = "select * from event e where e.eventStartTime like ?1%" ,nativeQuery = true)
    public List<Event> findEventByDate(String date);
}

