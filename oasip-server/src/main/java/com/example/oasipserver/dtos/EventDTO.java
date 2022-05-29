package com.example.oasipserver.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer id;
    @NotEmpty()
    @Size(min = 1, max = 100 , message = "Name lenght should be between 1 and 100 characters!!")
    private String bookingName;
    @NotEmpty()
    @Size(max = 50 , message = "Email lenght should not be more than 50 characters!!")
    @Email(message = "Invalid Email type or format!!")
    private String bookingEmail;
    @NotNull()
    private Integer categoryId;
    private String categoryName;
    @NotNull()
    @Future(message = "The date and time should be in the future for the appointment.")
    private ZonedDateTime eventStartTime;
    private Integer eventDuration;
    @Size(min = 0 , max = 500)
    private String eventNotes;

}

