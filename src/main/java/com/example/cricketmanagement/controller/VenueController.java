package com.example.cricketmanagement.controller;

import com.example.cricketmanagement.dto.CreateVenueDTO;
import com.example.cricketmanagement.dto.GetVenueDTO;
import com.example.cricketmanagement.service.VenueService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/venue")
public class VenueController {

    public final VenueService venueService;

    @PostMapping()
    public CreateVenueDTO createVenue(@RequestBody CreateVenueDTO createVenueDTO){
        return venueService.createVenue(createVenueDTO);
    }

    @GetMapping("/{id}")
    public GetVenueDTO getVenue(@PathVariable Long id){
        return venueService.getVenue(id);
    }

    @GetMapping()
    public List<GetVenueDTO> getAllVenues(){
        return venueService.getAllVenues();
    }

    @PutMapping("/{id}")
    public CreateVenueDTO updateVenue(@PathVariable Long id,
                                      @RequestBody CreateVenueDTO venue){
        return venueService.updateVenue(id, venue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable Long id){
        return venueService.deleteVenue(id);
    }

}
