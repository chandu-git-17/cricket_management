package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.CricketBoardCreationDTO;
import com.example.cricketmanagement.exceptions.CreationErrorException;
import com.example.cricketmanagement.exceptions.RecordNotFoundException;
import com.example.cricketmanagement.model.CricketBoard;
import com.example.cricketmanagement.model.Status;
import com.example.cricketmanagement.repository.CricketBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CricketBoardService implements CricketBoardInterface{

    public final CricketBoardRepository cricketBoardRepository;

    private CricketBoardCreationDTO convertResultObjectOfCricketBoard(CricketBoard cricketBoard){
        CricketBoardCreationDTO cricketBoardCreationDTO = new CricketBoardCreationDTO();
        cricketBoardCreationDTO.setBoardCode(cricketBoard.getBoardCode());
        cricketBoardCreationDTO.setId(cricketBoard.getId());
        return cricketBoardCreationDTO;
    }

    @Override
    public CricketBoardCreationDTO createCricketBoard(CricketBoard cricketBoardObject) {
        if(cricketBoardRepository
                .existsCricketBoardByBoardCode(cricketBoardObject.getBoardCode())){
            throw new CreationErrorException("Cricket Board already exists");
        }
        CricketBoard result = cricketBoardRepository.save(cricketBoardObject);
        return convertResultObjectOfCricketBoard(result);
    }

    @Override
    public CricketBoard getCricketBoard(Long id) {
        return cricketBoardRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No records found with this id: " + id)
        );
    }

    @Override
    public List<CricketBoard> getAllCricketBoards() {
        return cricketBoardRepository.findCricketBoardByStatus(Status.ACTIVE);
    }

    @Override
    public CricketBoard updateCricketBoard(Long id, CricketBoard cricketBoardObject) {
        CricketBoard existingCricketBoard = cricketBoardRepository.findById(id).
                orElseThrow(() -> new RecordNotFoundException("Record not found with id: " + id));
        if(cricketBoardObject.getBoardName() != null &&
                !cricketBoardObject.getBoardName().isBlank()){
            existingCricketBoard.setBoardName(cricketBoardObject.getBoardName());
        }
        if(cricketBoardObject.getBoardCode() != null &&
                !cricketBoardObject.getBoardCode().isBlank()){
            existingCricketBoard.setBoardCode(cricketBoardObject.getBoardCode());
        }
        if(cricketBoardObject.getHeadQuarters() != null &&
                !cricketBoardObject.getHeadQuarters().isBlank()){
            existingCricketBoard.setHeadQuarters(cricketBoardObject.getHeadQuarters());
        }
        if(cricketBoardObject.getPresident() != null &&
                !cricketBoardObject.getPresident().isBlank()){
            existingCricketBoard.setPresident(cricketBoardObject.getPresident());
        }
        if(cricketBoardObject.getEstablishedYear() != null &&
                !cricketBoardObject.getEstablishedYear().isBlank()){
            existingCricketBoard.setEstablishedYear(cricketBoardObject.getEstablishedYear());
        }
        if(cricketBoardObject.getStatus() != null)  {
            existingCricketBoard.setStatus(cricketBoardObject.getStatus());
        }
        return cricketBoardRepository.save(existingCricketBoard);
    }

    @Override
    public ResponseEntity<String> deleteCricketBoard(Long id) {
        CricketBoard cricketBoard = cricketBoardRepository.getCricketBoardsById(id);
        cricketBoard.setStatus(Status.INACTIVE);
        cricketBoardRepository.save(cricketBoard);
        return ResponseEntity.status(204).body(id + " Cricket board is deleted");
    }


}
