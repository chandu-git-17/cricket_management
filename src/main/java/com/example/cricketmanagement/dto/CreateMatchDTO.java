package com.example.cricketmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateMatchDTO {

    private Long id;
    @NotNull
    private Long team1Id;
    @NotNull
    private Long team2Id;
    @NotNull
    private Long tournamentId;
    @NotNull
    private LocalDate matchDate;
    private String matchStatus;
    @NotNull
    private Long venueId;

}
