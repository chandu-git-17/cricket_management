package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CreateTournamentDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.exceptions.RecordNotFoundException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.Tournament;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.TournamentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private CreateTournamentDTO convertToCreateTournamentDTO(Tournament tournament){
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
        Optional<Tournament> existingTournament = tournamentRepository.findByName(tournament.getName());
        if(existingTournament.isPresent()){
            throw new CreationErrorException("Tournament already exists");
        }
        Tournament newTournament = getNewTournament(tournament);
        return convertToCreateTournamentDTO(tournamentRepository.save(newTournament));
    }

    private @NonNull Tournament getNewTournament(CreateTournamentDTO tournament) {
        Country country = countryRepository.findById(tournament.getHostCountryId())
                .orElseThrow(() -> new RecordNotFoundException("Host country not found"));
        Set<Country> participatingCountries =
                StreamSupport.stream(
                        countryRepository.findAllById(tournament.getParticipatingCountriesIds()).spliterator(),
                        false
                ).collect(Collectors.toSet());Tournament newTournament = new Tournament();
        if(tournament.getParticipatingCountriesIds().size() !=
                participatingCountries.size()){
            throw new CreationErrorException("Participating coutries does not exist, please create countries first");
        }
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
        return newTournament;
    }

    public CreateTournamentDTO getTournament(Long id){
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Tournament found with id: " + id));
        return convertToCreateTournamentDTO(tournament);
    }

    public List<CreateTournamentDTO> getAllTournaments(){
        List<Tournament> allTournaments = tournamentRepository.findAll();
        List<CreateTournamentDTO> returnList = new ArrayList<>();
        for(Tournament tournament: allTournaments){
            returnList.add(convertToCreateTournamentDTO(tournament));
        }
        return returnList;
    }

    public CreateTournamentDTO updateTournament(Long id, CreateTournamentDTO tournamentDTO){
        Tournament existingTournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Tournament found with this id: " + id));

        if(tournamentDTO.getTournamentType() != null){
            existingTournament.setTournamentType(tournamentDTO.getTournamentType());
        }
        if(tournamentDTO.getNumberOfTeams() != null){
            existingTournament.setNumberOfTeams(tournamentDTO.getNumberOfTeams());
        }
        if(tournamentDTO.getCode() != null){
            existingTournament.setCode(tournamentDTO.getCode());
        }
        if(tournamentDTO.getStatus() != null){
            existingTournament.setStatus(tournamentDTO.getStatus());
        }
        if(tournamentDTO.getName() != null){
            existingTournament.setName(existingTournament.getName());
        }
        if(tournamentDTO.getMatchFormat() != null){
            existingTournament.setMatchFormat(existingTournament.getMatchFormat());
        }
        if(tournamentDTO.getParticipatingCountriesIds() != null){
            Set<Country> participatingCountries =
                    StreamSupport.stream(
                            countryRepository.findAllById(tournamentDTO.getParticipatingCountriesIds()).spliterator(),
                            false
                    ).collect(Collectors.toSet());
            existingTournament.setParticipatingCountries(participatingCountries);
        }
        if(tournamentDTO.getStartDate() != null){
            existingTournament.setStartDate(tournamentDTO.getStartDate());
        }
        if(tournamentDTO.getEndDate() != null){
            existingTournament.setEndDate(tournamentDTO.getEndDate());
        }
        if(tournamentDTO.getHostCountryId() != null){
            Country country = countryRepository.findById(tournamentDTO.getHostCountryId())
                    .orElseThrow(() -> new RecordNotFoundException("Host country not found"));
            existingTournament.setHostCountry(country);
        }
        return convertToCreateTournamentDTO(tournamentRepository.save(existingTournament));
    }

    public ResponseEntity<String> deleteTournament(Long id){
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No tournament found with id: " + id));
        tournamentRepository.deleteById(id);
        return ResponseEntity.status(204).body(tournament.getName() + " is deleted");
    }


}
