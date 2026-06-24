package com.example.cricketmanagement.repository;

import com.example.cricketmanagement.model.CricketBoard;
import com.example.cricketmanagement.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CricketBoardRepository extends JpaRepository<CricketBoard, Long> {
    CricketBoard getCricketBoardsById(Long id);
    Boolean existsCricketBoardByBoardCode(String boardCode);
    List<CricketBoard> findCricketBoardByStatus(Status status);
}
