package com.example.cricketmanagement.repository;

import com.example.cricketmanagement.model.CricketBoard;
import com.example.cricketmanagement.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CricketBoardRepository extends JpaRepository<CricketBoard, Long> {
    CricketBoard getCricketBoardsById(Long id);
    List<CricketBoard> findCricketBoardByStatus(Status status);

    Optional<CricketBoard> getCricketBoardsByBoardCode(String boardCode);
}
