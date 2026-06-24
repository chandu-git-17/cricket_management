package com.example.cricketmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cricket_boards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CricketBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false, unique = true)
    private String boardCode;

    private String president;
    private String headQuarters;
    private String establishedYear;

    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private Status status;
}
