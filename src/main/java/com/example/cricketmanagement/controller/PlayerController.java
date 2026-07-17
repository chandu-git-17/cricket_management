package com.example.cricketmanagement.controller;

import com.example.cricketmanagement.dto.CreatePlayerDTO;
import com.example.cricketmanagement.dto.GetPlayerDTO;
import com.example.cricketmanagement.model.Player;
import com.example.cricketmanagement.service.CountryService;
import com.example.cricketmanagement.service.CricketBoardService;
import com.example.cricketmanagement.service.PlayerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    public final PlayerService playerService;
    public final CountryService countryService;
    public final CricketBoardService cricketBoardService;

    @PostMapping()
    public CreatePlayerDTO createPlayer(@RequestBody CreatePlayerDTO player){
        return playerService.createPlayer(player);
    }

    @GetMapping("/{id}")
    public GetPlayerDTO getPlayer(@PathVariable Long id){
        return playerService.getPlayer(id);
    }

    @GetMapping()
    public List<GetPlayerDTO> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @PutMapping("/{id}")
    public CreatePlayerDTO updatePlayer(@PathVariable Long id, @RequestBody CreatePlayerDTO createPlayerDTO){
        return playerService.updatePlayer(id, createPlayerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id){
        return playerService.deletePlayer(id);
    }

}
