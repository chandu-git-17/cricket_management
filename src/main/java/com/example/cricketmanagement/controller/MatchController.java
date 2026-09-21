package com.example.cricketmanagement.controller;

import com.example.cricketmanagement.dto.CreateMatchDTO;
import com.example.cricketmanagement.model.MatchStatus;
import com.example.cricketmanagement.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    public final MatchService matchService;

    @PostMapping()
    public CreateMatchDTO createMatch(@RequestBody CreateMatchDTO match){
        return matchService.createMatch(match);
    }

    @GetMapping()
    public List<CreateMatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateMatchDTO> getMatchById(@PathVariable Long id){
        return matchService.getMatchById(id);
    }
}
