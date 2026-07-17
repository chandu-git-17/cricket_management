package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CreatePlayerDTO;
import com.example.cricketmanagement.dto.GetPlayerDTO;
import com.example.cricketmanagement.exceptions.DuplicateFoundException;
import com.example.cricketmanagement.exceptions.RecordNotFoundException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.CricketBoard;
import com.example.cricketmanagement.model.Player;
import com.example.cricketmanagement.model.PlayerRole;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.CricketBoardRepository;
import com.example.cricketmanagement.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

     public final PlayerRepository playerRepository;
     public final CountryRepository countryRepository;
     public final CricketBoardRepository cricketBoardRepository;

     private CreatePlayerDTO convertToCreatePLayerDTO(Player player){
         CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO();
         createPlayerDTO.setId(player.getId());
         createPlayerDTO.setPlayerName(player.getPlayerName());
         createPlayerDTO.setDob(player.getDob());
         createPlayerDTO.setCountryId(player.getCountry().getId());
         createPlayerDTO.setRole(player.getRole());
         createPlayerDTO.setBattingStyle(player.getBattingStyle());
         createPlayerDTO.setBowlingStyle(player.getBowlingStyle());
         createPlayerDTO.setJerseyNumber(player.getJerseyNumber());
         return createPlayerDTO;
     }

     public void isDuplicatePlayer(CreatePlayerDTO createPlayerDTO){
         if(createPlayerDTO.getForceAdd()){
             return;
         }

         Optional<Player> existingPlayer =
                 playerRepository.findDuplicatePlayer(
                         createPlayerDTO.getPlayerName(),
                         createPlayerDTO.getJerseyNumber(),
                         createPlayerDTO.getDob(),
                         createPlayerDTO.getCountryId()
                 );
         if(existingPlayer.isPresent()){
             throw new DuplicateFoundException("Player already exists");
         }
     }

     public CreatePlayerDTO createPlayer(CreatePlayerDTO createPlayerDTO){
         isDuplicatePlayer(createPlayerDTO);
         Country country = countryRepository
                 .findById(createPlayerDTO.getCountryId())
                 .orElseThrow(() ->
                         new RecordNotFoundException("No country found with id" + createPlayerDTO.getId()));
         Player player = new Player();
         player.setPlayerName(createPlayerDTO.getPlayerName());
         player.setDob(createPlayerDTO.getDob());
         player.setRole(createPlayerDTO.getRole());
         player.setBattingStyle(createPlayerDTO.getBattingStyle());
         player.setBowlingStyle(createPlayerDTO.getBowlingStyle());
         player.setJerseyNumber(createPlayerDTO.getJerseyNumber());
         player.setCountry(country);
         return convertToCreatePLayerDTO(playerRepository.save(player));

     }

     public GetPlayerDTO convertToGetPlayerDTO(Player player){
         GetPlayerDTO getPlayerDTO = new GetPlayerDTO();
         getPlayerDTO.setPlayerName(player.getPlayerName());
         getPlayerDTO.setId(player.getId());
         getPlayerDTO.setJerseyNumber(player.getJerseyNumber());
         getPlayerDTO.setBattingStyle(player.getBattingStyle());
         getPlayerDTO.setBowlingStyle(player.getBowlingStyle());
         getPlayerDTO.setCountryId(player.getId());
         return getPlayerDTO;
     }

     public GetPlayerDTO getPlayer(Long id){
         Player player = playerRepository.findById(id)
                 .orElseThrow(() -> new RecordNotFoundException("No Player found with the id " + id));
         return convertToGetPlayerDTO(player);
     }

     public List<GetPlayerDTO> getAllPlayers(){
         List<Player> players = playerRepository.findAll();
         if(players.isEmpty()){
             throw new RecordNotFoundException("No Players found");
         }
         List<GetPlayerDTO> returnObject = new ArrayList<>();
         for(Player player: players){
             returnObject.add(convertToGetPlayerDTO(player));
         }
         return returnObject;
     }

     public CreatePlayerDTO updatePlayer(Long id, CreatePlayerDTO updatePlayer){
         Player existingplayer = playerRepository.findById(id)
                 .orElseThrow(() -> new RecordNotFoundException("No player found with id: " + id));
         if(updatePlayer.getPlayerName() != null){
             existingplayer.setPlayerName(updatePlayer.getPlayerName());
         }
         if(updatePlayer.getJerseyNumber() != null){
             existingplayer.setJerseyNumber(updatePlayer.getJerseyNumber());
         }
         if(updatePlayer.getDob() != null){
             existingplayer.setDob(updatePlayer.getDob());
         }
         if(updatePlayer.getCountryId() != null){
             existingplayer.getCountry().setId(updatePlayer.getCountryId());
         }
         if(updatePlayer.getRole() != null){
             existingplayer.setRole(updatePlayer.getRole());
         }
         if(updatePlayer.getBattingStyle() != null){
             existingplayer.setBattingStyle(updatePlayer.getBattingStyle());
         }
         if(updatePlayer.getBowlingStyle() != null){
             existingplayer.setBowlingStyle(updatePlayer.getBowlingStyle());
         }
         return convertToCreatePLayerDTO(playerRepository.save(existingplayer));
     }

     public ResponseEntity<String> deletePlayer(Long id){
         Player player = playerRepository.findById(id)
                 .orElseThrow(() -> new RecordNotFoundException("No player found with id: " + id));
         playerRepository.deleteById(id);
         return ResponseEntity.status(204).body("Player " + player.getPlayerName() +  " is deleted");
     }


}
