package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CreateMatchDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.Match;
import com.example.cricketmanagement.model.Tournament;
import com.example.cricketmanagement.model.Venue;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.MatchRepository;
import com.example.cricketmanagement.repository.TournamentRepository;
import com.example.cricketmanagement.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CountryRepository countryRepository;
    private final VenueRepository venueRepository;
    private final TournamentRepository tournamentRepository;

    private CreateMatchDTO matchToCreateMatchDTO(Match match){
        CreateMatchDTO createMatchDTO = new CreateMatchDTO();
        createMatchDTO.setMatchStatus(match.getMatchStatus());
        createMatchDTO.setMatchDate(match.getMatchDate());
        createMatchDTO.setId(match.getId());
        createMatchDTO.setTournamentId(match.getTournament().getId());
        createMatchDTO.setTeam1Id(match.getTeam1().getId());
        createMatchDTO.setTeam2Id(match.getTeam2().getId());
        createMatchDTO.setVenueId(match.getVenue().getId());
        return createMatchDTO;
    }

    public CreateMatchDTO createMatch(CreateMatchDTO match){
        Country team1 = countryRepository.findById(match.getTeam1Id())
                .orElseThrow(() -> new CreationErrorException("Team 1 not found"));
        Country team2 = countryRepository.findById(match.getTeam2Id())
                .orElseThrow(() -> new CreationErrorException("Team 2 not found"));
        if(team1.getId().equals(team2.getId())){
            throw new CreationErrorException("Please provide 2 different teams.");
        }
        Tournament tournament = tournamentRepository.findById(match.getTournamentId())
                .orElseThrow(() -> new CreationErrorException("Tournament not found"));
        Set<Long> participatingCountriesIds = tournament.getParticipatingCountries().stream()
                .map(Country::getId).collect(Collectors.toSet());
        if(!participatingCountriesIds.contains(team1.getId()) || !participatingCountriesIds.contains(team2.getId())){
            throw new CreationErrorException("Both the teams should be included in the tournament");
        }
        Venue venue = venueRepository.findById(match.getVenueId())
                .orElseThrow(() -> new CreationErrorException("Venue not found"));
        Match saveMatch = new Match();
        saveMatch.setMatchDate(match.getMatchDate());
        saveMatch.setVenue(venue);
        saveMatch.setTournament(tournament);
        saveMatch.setTeam1(team1);
        saveMatch.setTeam2(team2);
        saveMatch.setMatchStatus(match.getMatchStatus());
        return matchToCreateMatchDTO(matchRepository.save(saveMatch));
    }

        public List<CreateMatchDTO> getAllMatches(){
            List<Match> allMatches = matchRepository.findAll();
            return allMatches.stream().map(this::matchToCreateMatchDTO).toList();
        }

        public ResponseEntity<CreateMatchDTO> getMatchById(Long id){
            Match match = matchRepository.findById(id)
                    .orElseThrow(() -> new CreationErrorException("No match found with this id:" + id));
            return ResponseEntity.status(HttpStatus.OK).body(matchToCreateMatchDTO(match));
        }

        public CreateMatchDTO updateMatch(Long id, CreateMatchDTO match){
            Country team1 = countryRepository.findById(match.getTeam1Id())
                    .orElseThrow(() -> new CreationErrorException("Team 1 not found"));
            Country team2 = countryRepository.findById(match.getTeam2Id())
                    .orElseThrow(() -> new CreationErrorException("Team 2 not found"));
            if(team1.getId().equals(team2.getId())){
                throw new CreationErrorException("Please provide 2 different teams.");
            }
            Tournament tournament = tournamentRepository.findById(match.getTournamentId())
                    .orElseThrow(() -> new CreationErrorException("Tournament not found"));
            Set<Long> participatingCountriesIds = tournament.getParticipatingCountries().stream()
                    .map(Country::getId).collect(Collectors.toSet());
            if(!participatingCountriesIds.contains(team1.getId()) || !participatingCountriesIds.contains(team2.getId())){
                throw new CreationErrorException("Both the teams should be included in the tournament");
            }
            Venue venue = venueRepository.findById(match.getVenueId())
                    .orElseThrow(() -> new CreationErrorException("Venue not found"));
            return null;
        }

}
