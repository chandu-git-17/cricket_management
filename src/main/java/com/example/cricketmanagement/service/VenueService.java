package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CreateVenueDTO;
import com.example.cricketmanagement.dto.GetVenueDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.exceptions.DuplicateFoundException;
import com.example.cricketmanagement.exceptions.RecordNotFoundException;
import com.example.cricketmanagement.model.Country;
import com.example.cricketmanagement.model.Status;
import com.example.cricketmanagement.model.Venue;
import com.example.cricketmanagement.repository.CountryRepository;
import com.example.cricketmanagement.repository.VenueRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class VenueService {

    public final VenueRepository venueRepository;
    public final CountryRepository countryRepository;

    public CreateVenueDTO convertToCreateVenueDTO(@NonNull Venue venue){
        CreateVenueDTO createVenueDTO = new CreateVenueDTO();
        createVenueDTO.setVenueShortName(venue.getVenueShortName());
        createVenueDTO.setVenueName(venue.getVenueName());
        createVenueDTO.setCapacity(venue.getCapacity());
        createVenueDTO.setCity(venue.getCity());
        createVenueDTO.setCountryId(venue.getCountry().getId());
        createVenueDTO.setEstablishedYear(venue.getEstablishedYear());
        createVenueDTO.setStatus(venue.getStatus());
        return createVenueDTO;
    }

    public CreateVenueDTO createVenue(CreateVenueDTO venue){

        Optional<Venue> existingVenue = venueRepository.findVenueByVenueName(venue.getVenueName());
        if(existingVenue.isPresent()){
            throw new DuplicateFoundException("Venue already present");
        }
        Country country = countryRepository.findById(venue.getCountryId())
                .orElseThrow(() -> new RecordNotFoundException("No Country exists"));
        if(venue.getStatus() != Status.ACTIVE){
            throw new CreationErrorException("Venue status should be active");
        }
        Venue createVenue = new Venue();
        createVenue.setVenueName(venue.getVenueName());
        createVenue.setVenueShortName(venue.getVenueShortName());
        createVenue.setCity(venue.getCity());
        createVenue.setCapacity(venue.getCapacity());
        createVenue.setStatus(venue.getStatus());
        createVenue.setCountry(country);
        createVenue.setEstablishedYear(venue.getEstablishedYear());
        Venue returnVenue =  venueRepository.save(createVenue);
        return convertToCreateVenueDTO(returnVenue);
    }

    private GetVenueDTO convertVenueToGetVenueDTO(Venue venue){
        GetVenueDTO returnVenue = new GetVenueDTO();
        returnVenue.setId(venue.getId());
        returnVenue.setVenueName(venue.getVenueName());
        returnVenue.setVenueShortName(venue.getVenueShortName());
        returnVenue.setCity(venue.getCity());
        returnVenue.setCapacity(venue.getCapacity());
        returnVenue.setEstablishedYear(venue.getEstablishedYear());
        returnVenue.setStatus(venue.getStatus());
        returnVenue.setCountryId(venue.getCountry().getId());
        return returnVenue;
    }

    public GetVenueDTO getVenue(Long id){
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Venue found with id: " + id));
        return convertVenueToGetVenueDTO(venue);
    }

    public List<GetVenueDTO> getAllVenues(){
        List<Venue> allVenues = venueRepository.findAll();
        if(allVenues.isEmpty()){
            throw new RecordNotFoundException("No Venues found");
        }
        List<GetVenueDTO> allVenuesDTO = new ArrayList<>();
        for(Venue venue: allVenues){
            allVenuesDTO.add(convertVenueToGetVenueDTO(venue));
        }
        return allVenuesDTO;
    }

    public CreateVenueDTO updateVenue(Long id, CreateVenueDTO venue){
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Venue found with id: " + id));
        if(venue.getCountryId() != null){
            Country country = countryRepository.findById(venue.getCountryId())
                    .orElseThrow(() -> new RecordNotFoundException("No country found with id: " + id));
            existingVenue.setCountry(country);
        }
        if(venue.getEstablishedYear() != null){
            existingVenue.setEstablishedYear(venue.getEstablishedYear());
        }
        if(venue.getVenueShortName() != null){
            existingVenue.setVenueShortName(venue.getVenueShortName());
        }
        if(venue.getVenueName() != null){
            existingVenue.setVenueName(venue.getVenueName());
        }
        if(venue.getCity() != null){
            existingVenue.setCity(venue.getCity());
        }
        if(venue.getCapacity() != null){
            existingVenue.setCapacity(venue.getCapacity());
        }
        if(venue.getStatus() != null){
            existingVenue.setStatus(venue.getStatus());
        }

        return convertToCreateVenueDTO(venueRepository.save(existingVenue));
    }

    public ResponseEntity<String> deleteVenue(Long id){
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No venue found to delete with id: " + id));
        venueRepository.deleteById(id);
        return ResponseEntity.status(204).body(venue.getVenueName() + " is deleted");
    }

}
