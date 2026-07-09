package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CountryCreationDTO;
import com.example.cricketmanagement.dto.GetCountryDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.exceptions.RecordNotFoundException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.CricketBoard;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.CricketBoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    public CountryRepository countryRepository;
    public CricketBoardRepository cricketBoardRepository;
    public CountryService(CountryRepository countryRepository, CricketBoardRepository cricketBoardRepository, ThreadPoolTaskExecutor threadPoolTaskExecutor){
        this.countryRepository = countryRepository;
        this.cricketBoardRepository = cricketBoardRepository;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public Country createCountry(Country country){
        Optional<Country> existingCountry = countryRepository
                .getCountriesByCountryCode(country.getCountryCode());
        if(existingCountry.isPresent()){
            throw new CreationErrorException("Country already exists");
        }
        return countryRepository.save(country);
    }

    public GetCountryDTO convertCountryToGetCountry(Country country){
        GetCountryDTO getCountryDTO = new GetCountryDTO();
        getCountryDTO.setId(country.getId());
        getCountryDTO.setCountryCode(country.getCountryCode());
        getCountryDTO.setCountryName(country.getCountryName());
        getCountryDTO.setCoach(country.getCoach());
        getCountryDTO.setBoardId(country.getCricketBoard().getId());
        return getCountryDTO;
    }

    public CountryCreationDTO convertCountryToUpdateCountry(Country country){
        CountryCreationDTO countryCreationDTO = new CountryCreationDTO();
        countryCreationDTO.setCountryCode(country.getCountryCode());
        countryCreationDTO.setCountryName(country.getCountryName());
        countryCreationDTO.setCoach(country.getCoach());
        countryCreationDTO.setCricketBoardId(country.getCricketBoard().getId());
        return countryCreationDTO;
    }

    public GetCountryDTO getCountry(Long id){
        Optional<Country> country = countryRepository.getCountriesById(id);
        if(country.isEmpty()){
            throw new RecordNotFoundException("No Country Found");
        }
        return convertCountryToGetCountry(country.get());
    }

    public List<GetCountryDTO> getAllCountries(){
        List<Country> allCountries = countryRepository.findAll();
        if(allCountries.isEmpty()){
            throw new RecordNotFoundException("No Countries Found");
        }
        List<GetCountryDTO> allCountriesDTO = new ArrayList<>();
        for(Country country: allCountries){
            allCountriesDTO.add(convertCountryToGetCountry(country));
        }
        return allCountriesDTO;
    }

    public CountryCreationDTO updateCountry(Long id, Country country){
        Optional<Country> existingCountry = countryRepository.getCountriesById(id);
        if(existingCountry.isEmpty()){
            throw new RecordNotFoundException("No country exists with id: " + id);
        }
        CricketBoard cricketBoard;
        if(country.getCricketBoard() != null && country.getCricketBoard().getId() != null) {
            cricketBoard = cricketBoardRepository
                            .findById(country.getCricketBoard().getId())
                            .orElseThrow(() -> new RecordNotFoundException("No Cricket board found to update"));
            Boolean checkCricketBoardAvailability = checkCountryExists(id);
            if(!checkCricketBoardAvailability)
            existingCountry.get().setCricketBoard(cricketBoard);
            else
                throw new InputMismatchException("Cricket board already assigned to another country");
        }
        if(country.getCountryName() != null){
            existingCountry.get().setCountryName(country.getCountryName());
        }
        if(country.getCountryCode() != null){
            existingCountry.get().setCountryCode(country.getCountryCode());
        }
        if(country.getCoach() != null) {
            existingCountry.get().setCoach(country.getCoach());
        }
        return convertCountryToUpdateCountry(countryRepository.save(existingCountry.get()));
    }

    public Boolean checkCountryExists(Long id){
        Optional<Country> existingCountry = countryRepository.getCountryByCricketBoard_Id(id);
        return existingCountry.isPresent();
    }

    public void deleteCountry(Long id){
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Country found to delete"));
        countryRepository.deleteById(id);
    }


}
