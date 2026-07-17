package com.example.cricketmanagement.dto;

import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVenueDTO {

    private String venueName;
    private String venueShortName;
    private String city;
    private Integer establishedYear;
    private Integer capacity;
    private Status status;
    private Long countryId;


}
