package com.example.cricketmanagement.dto;

import com.example.cricketmanagement.model.MatchStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateMatchDTO {

    private Long id;
    @NotNull(message = "Team 1 is required")
    private Long team1Id;
    @NotNull(message = "Team 2 is required")
    private Long team2Id;
    @NotNull(message = "Tournament is required")
    private Long tournamentId;
    @NotNull(message = "Match date is required")
    private LocalDate matchDate;
    @NotNull(message = "Match status is required")
    private MatchStatus matchStatus;
    @NotNull(message = "Venue is required")
    private Long venueId;

}
