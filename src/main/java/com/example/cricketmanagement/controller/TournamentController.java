package com.example.cricketmanagement.controller;

import com.example.cricketmanagement.dto.CreateTournamentDTO;
import com.example.cricketmanagement.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournament")
@RequiredArgsConstructor
public class TournamentController {

    public final TournamentService tournamentService;

    @PostMapping()
    public CreateTournamentDTO createTournament(@RequestBody CreateTournamentDTO tournamentDTO){
        return tournamentService.createTournament(tournamentDTO);
    }

    @GetMapping("/{id}")
    public CreateTournamentDTO getTournament(@PathVariable Long id){
        return tournamentService.getTournament(id);
    }

    @GetMapping()
    public List<CreateTournamentDTO> getAllTournaments(){
        return tournamentService.getAllTournaments();
    }

    @PutMapping("/{id}")
    public CreateTournamentDTO updateTournament(@PathVariable Long id,
                                                @RequestBody CreateTournamentDTO tournamentDTO){
        return tournamentService.updateTournament(id, tournamentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTournament(@PathVariable Long id){
        return tournamentService.deleteTournament(id);
    }
}
