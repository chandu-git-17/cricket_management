package com.example.cricketmanagement.repository;

import com.example.cricketmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    Optional<Venue> findVenueByVenueName(String venueName);
}
