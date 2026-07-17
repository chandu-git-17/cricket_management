package com.example.cricketmanagement.dto;

import com.example.cricketmanagement.model.BattingStyle;
import com.example.cricketmanagement.model.BowlingStyle;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.PlayerRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreatePlayerDTO {

    private Long id;
    private String playerName;
    private Integer jerseyNumber;
    private Date dob;
    private PlayerRole role;
    private BattingStyle battingStyle;
    private BowlingStyle bowlingStyle;
    private Long countryId;
    private Boolean forceAdd = false;

}
