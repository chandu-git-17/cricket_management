package com.example.cricketmanagement.repository;

import com.example.cricketmanagement.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> getCountriesByCountryCode(String countryCode);

    Optional<Country> getCountriesById(Long id);

    Optional<Country> getCountryByCricketBoard_Id(Long cricketBoardId);

    Optional<Object> findCountryByIdIs(Long id);
}
