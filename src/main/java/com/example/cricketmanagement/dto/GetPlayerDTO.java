package com.example.cricketmanagement.dto;

import com.example.cricketmanagement.model.BattingStyle;
import com.example.cricketmanagement.model.BowlingStyle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPlayerDTO {

    private Long id;
    private String playerName;
    private Integer jerseyNumber;
    private BattingStyle battingStyle;
    private BowlingStyle bowlingStyle;
    private Long countryId;

}
