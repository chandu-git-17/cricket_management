package com.example.cricketmanagement.repository;

import com.example.cricketmanagement.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query(
            """
            select p from Player p
            where p.playerName = :playerName
                        and p.jerseyNumber = :jerseyNumber
                                    and p.dob = :dob
                                                and p.country.id = :countryId
            """
    )
    Optional<Player> findDuplicatePlayer(
            @Param("playerName") String playerName,
            @Param("jerseyNumber") Integer jerseyNumber,
            @Param("dob") Date dob,
            @Param("countryId") Long countryId

    );

}
