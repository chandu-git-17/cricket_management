package com.example.cricketmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(unique = true)
    private String code;
    private Integer numberOfTeams;

    @Enumerated(EnumType.STRING)
    private MatchFormat matchFormat;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private TournamentType tournamentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country hostCountry;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tournament_countries",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> participatingCountries;



}
