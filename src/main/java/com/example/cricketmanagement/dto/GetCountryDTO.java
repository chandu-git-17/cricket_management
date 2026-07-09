package com.example.cricketmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCountryDTO {

    private Long id;
    private String countryName;
    private String countryCode;
    private String coach;
    private Long boardId;

}
