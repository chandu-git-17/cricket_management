package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CreateTournamentDTO;
import com.example.cricketmanagement.exceptions.RecordNotFoundException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.Tournament;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.TournamentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class TournamentService {

    public final TournamentRepository tournamentRepository;
    public final CountryRepository countryRepository;

    private CreateTournamentDTO covertToCreateTournamentDTO(Tournament tournament){
        Set<Long> participatingCountriesIds = tournament.getParticipatingCountries().stream()
                .map(Country::getId).collect(Collectors.toSet());
        return CreateTournamentDTO.builder()
                .id(tournament.getId())
                .code(tournament.getCode())
                .name(tournament.getName())
                .tournamentType(tournament.getTournamentType())
                .numberOfTeams(tournament.getNumberOfTeams())
                .hostCountryId(tournament.getHostCountry().getId())
                .matchFormat(tournament.getMatchFormat())
                .participatingCountriesIds(participatingCountriesIds)
                .status(tournament.getStatus())
                .startDate(tournament.getStartDate())
                .endDate(tournament.getEndDate())
                .build();
    }

    public CreateTournamentDTO createTournament(CreateTournamentDTO tournament){
        Country country = countryRepository.findById(tournament.getHostCountryId())
                .orElseThrow(() -> new RecordNotFoundException("Host country not found"));
        Set<Country> participatingCountries =
                StreamSupport.stream(
                        countryRepository.findAllById(tournament.getParticipatingCountriesIds()).spliterator(),
                        false
                ).collect(Collectors.toSet());
        Tournament newTournament = new Tournament();
        newTournament.setCode(tournament.getCode());
        newTournament.setName(tournament.getName());
        newTournament.setTournamentType(tournament.getTournamentType());
        newTournament.setEndDate(tournament.getEndDate());
        newTournament.setHostCountry(country);
        newTournament.setStatus(tournament.getStatus());
        newTournament.setMatchFormat(tournament.getMatchFormat());
        newTournament.setStartDate(tournament.getStartDate());
        newTournament.setNumberOfTeams(tournament.getNumberOfTeams());
        newTournament.setParticipatingCountries(participatingCountries);

    }

}
