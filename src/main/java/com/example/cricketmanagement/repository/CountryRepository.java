package com.example.cricketmanagement.repository;

import com.example.cricketmanagement.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
