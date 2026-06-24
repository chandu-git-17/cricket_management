package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CricketBoardCreationDTO;
import com.example.cricketmanagement.model.CricketBoard;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CricketBoardInterface {
    public CricketBoardCreationDTO createCricketBoard(CricketBoard c);
    public CricketBoard getCricketBoard(Long id);
    public List<CricketBoard> getAllCricketBoards();
    public CricketBoard updateCricketBoard(Long id, CricketBoard c);
    public ResponseEntity<String> deleteCricketBoard(Long id);
}
