package com.example.oasipserver.services;


import com.example.oasipserver.dtos.CategoryDTO;
import com.example.oasipserver.dtos.EventDTO;
import com.example.oasipserver.entities.Event;
import com.example.oasipserver.entities.Eventcategory;
import com.example.oasipserver.repositories.EventCategoryRepository;
import com.example.oasipserver.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EventCategoryService {
    @Autowired
    private EventCategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public List<CategoryDTO> getAllCategory(){
        List<Eventcategory> categeries = repository.findAll(Sort.by("Id").descending());
        return listMapper.mapList(categeries , CategoryDTO.class , modelMapper);
    }

    public List<EventDTO> getEventByCategory(Integer categoryId){
        Eventcategory eventcategory = repository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, categoryId + " Does not exist !!!")
        );
        Set<Event> eventSet = eventcategory.getEvents();
        List<Event> events = new ArrayList<>(eventSet);
        events.sort((o1,o2) -> o2.getEventStartTime().compareTo(o1.getEventStartTime()));
        return listMapper.mapList(events, EventDTO.class, modelMapper);
    }
    public Eventcategory updateCategory(Eventcategory updateCategory, Integer categoryId){
        Eventcategory category = repository.findById(categoryId).map(existCategory -> mapEvent(existCategory,updateCategory))
                .orElseGet(()->
                {
                    updateCategory.setId(categoryId);
                    return updateCategory;
                });
        List<Eventcategory> name = repository.uniqueCategoryName(updateCategory.getEventCategoryName().trim());
        if(name.size() != 0 && name.get(0).getEventCategoryName() != updateCategory.getEventCategoryName()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name must be unique.");

        }

        return repository.saveAndFlush(category);
    }
    private Eventcategory mapEvent(Eventcategory existCategory, Eventcategory updateCategory) {
        if (updateCategory.getEventCategoryName() != null) {
            existCategory.setEventCategoryName(updateCategory.getEventCategoryName());
        }
        if(updateCategory.getEventCategoryDescription() != null) {
            existCategory.setEventCategoryDescription(updateCategory.getEventCategoryDescription());
        }
        if(updateCategory.getEventDuration() != null) {
            existCategory.setEventDuration(updateCategory.getEventDuration());
        }
        return existCategory;
    }
}

