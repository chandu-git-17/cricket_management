package com.example.cricketmanagement.dto;

import com.example.cricketmanagement.model.MatchFormat;
import com.example.cricketmanagement.model.Status;
import com.example.cricketmanagement.model.TournamentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class CreateTournamentDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String code;
    private Integer numberOfTeams;
    private MatchFormat matchFormat;
    private Status status;
    private TournamentType tournamentType;
    private Long hostCountryId;
    private Set<Long> participatingCountriesIds;

}
