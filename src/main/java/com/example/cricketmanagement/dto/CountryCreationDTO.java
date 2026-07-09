package com.example.cricketmanagement.dto;

import com.example.cricketmanagement.model.CricketBoard;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CountryCreationDTO {

    @NotBlank(message = "Country code is mandatory.")
    @Size(min = 2, max = 3, message = "Country code should be between 2-3 characters only.")
    private String countryCode;

    @NotBlank(message = "Country name is mandatory.")
    private String countryName;

    private String coach;

    @NotNull(message = "Cricket board id is mandatory.")
    private Long cricketBoardId;

}
