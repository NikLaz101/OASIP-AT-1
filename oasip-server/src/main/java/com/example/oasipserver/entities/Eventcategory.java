package com.example.oasipserver.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Table(name = "eventcategory", indexes = {
        @Index(name = "eventCategoryName_UNIQUE", columnList = "eventCategoryName", unique = true)
})
@Entity
public class Eventcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventCategoryId", nullable = false)
    private Integer id;

    @Column(name = "eventCategoryName", length = 100, unique = true ,nullable = false )
    @NotNull(message = "Category name cant be empty!")
    @Size(min = 1 , max = 100)
    private String eventCategoryName;

    @Column(name = "eventCategoryDescription", length = 500)
    @Size(min = 0,max = 500)
    private String eventCategoryDescription;

    @Column(name = "eventDuration", nullable = false)
    @NotNull(message = "Event duration can not be empty")
    @Range(min = 1 , max = 480 , message = "Event duration is out of range!")
    private Integer eventDuration;

    @JsonIgnore
    @OneToMany(mappedBy = "eventCategory")
    private Set<Event> events = new LinkedHashSet<>();

}

