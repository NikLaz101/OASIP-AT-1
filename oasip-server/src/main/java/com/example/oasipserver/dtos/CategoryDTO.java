package com.example.oasipserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer id;
    @NotNull(message = "Category name can not be empty!")
    @Size(min = 1 , max = 100)
    private String eventCategoryName;
    @Size(min = 0,max = 500)
    private String eventCategoryDescription;
    @NotNull(message = "Event duration can not be empty")
    @Range(min = 1 , max = 480 , message = "Event duration is out of range!")
    private Integer eventDuration;
}

