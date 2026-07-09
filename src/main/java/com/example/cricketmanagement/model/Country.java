package com.example.cricketmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String countryName;

    @Column(nullable = false, unique = true)
    private String countryCode;

    private String coach;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private CricketBoard cricketBoard;

}
