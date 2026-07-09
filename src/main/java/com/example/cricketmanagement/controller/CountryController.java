package com.example.cricketmanagement.controller;

import com.example.cricketmanagement.dto.CountryCreationDTO;
import com.example.cricketmanagement.dto.GetCountryDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.CricketBoard;
import com.example.cricketmanagement.service.CountryService;
import com.example.cricketmanagement.service.CricketBoardService;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/country")
public class CountryController {

    public CountryService countryService;
    public CricketBoardService cricketBoardService;

    public CountryController(CountryService countryService, CricketBoardService cricketBoardService){
        this.countryService = countryService;
        this.cricketBoardService = cricketBoardService;
    }

    public CountryCreationDTO convertCountryCreationDTO(Country country){
        CountryCreationDTO countryCreationDTO = new CountryCreationDTO();
        countryCreationDTO.setCountryName(country.getCountryName());
        countryCreationDTO.setCountryCode(country.getCountryCode());
        countryCreationDTO.setCricketBoardId(country.getCricketBoard().getId());
        countryCreationDTO.setCoach(country.getCoach());
        return countryCreationDTO;
    }

    @PostMapping()
    public CountryCreationDTO createCountry(@RequestBody CountryCreationDTO countryCreationDTO){
        Country country = new Country();
        CricketBoard cricketBoard = cricketBoardService
                .getCricketBoard(countryCreationDTO.getCricketBoardId());
            Boolean assignedCountry = countryService.checkCountryExists(countryCreationDTO.getCricketBoardId());
            if (!assignedCountry) {
                country.setCountryName(countryCreationDTO.getCountryName());
                country.setCountryCode(countryCreationDTO.getCountryCode());
                country.setCoach(countryCreationDTO.getCoach());
                country.setCricketBoard(cricketBoard);
                return convertCountryCreationDTO(countryService.createCountry(country));
            } else {
                throw new CreationErrorException("Cricket board is already assigned to another country");
            }
    }

    @GetMapping("/{id}")
    public GetCountryDTO getCountry(@PathVariable Long id){
        return countryService.getCountry(id);
    }

    @GetMapping()
    public List<GetCountryDTO> getAllCountries(){
        return countryService.getAllCountries();
    }

    @PutMapping("/{id}")
    public CountryCreationDTO updateCountry(@PathVariable Long id, @RequestBody Country country){
        return countryService.updateCountry(id, country);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id){
        countryService.deleteCountry(id);
    }

}
