package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CreateMatchDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.Tournament;
import com.example.cricketmanagement.model.Venue;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.MatchRepository;
import com.example.cricketmanagement.repository.TournamentRepository;
import com.example.cricketmanagement.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CountryRepository countryRepository;
    private final VenueRepository venueRepository;
    private final TournamentRepository tournamentRepository;

    public CreateMatchDTO createMatch(CreateMatchDTO match){
        Country team1 = countryRepository.findById(match.getTeam1Id())
                .orElseThrow(() -> new CreationErrorException("Team 1 not found"));
        Country team2 = countryRepository.findById(match.getTeam2Id())
                .orElseThrow(() -> new CreationErrorException("Team 2 not found"));
        if(team1.getId().equals(team2.getId())){
            //null check in controller
            throw new CreationErrorException("Please provide 2 different teams.");
        }
        Tournament tournament = tournamentRepository.findById(match.getTournamentId())
                .orElseThrow(() -> new CreationErrorException("Tournament not found"));
        List<Long> participatingCountriesIds = tournament.getParticipatingCountries().stream()
                .map(Country::getId).toList();
        if(!participatingCountriesIds.contains(team1.getId()) || !participatingCountriesIds.contains(team2.getId())){
            throw new CreationErrorException("Both the teams should be included in the tournament");
        }
        Venue venue = venueRepository.findById(match.getVenueId())
                .orElseThrow(() -> new CreationErrorException("Venue not found"));

    }

}
