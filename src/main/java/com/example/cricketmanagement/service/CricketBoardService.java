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
import java.util.Optional;

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
        Optional<CricketBoard> existingCricketBoard = cricketBoardRepository
                .getCricketBoardsByBoardCode(cricketBoardObject.getBoardCode());
        CricketBoard result;
        if(cricketBoardObject.getStatus() != Status.ACTIVE){
            throw new CreationErrorException("Status of the new team board should be active...");
        }
        if(existingCricketBoard.isPresent()){
            CricketBoard cricketBoard = existingCricketBoard.get();
            if(cricketBoard.getStatus() == Status.ACTIVE){
                throw new CreationErrorException("Cricket board already exists...");
            }
            cricketBoard.setStatus(cricketBoardObject.getStatus());
            cricketBoard.setBoardName(cricketBoardObject.getBoardName());
            cricketBoard.setPresident(cricketBoardObject.getPresident());
            cricketBoard.setHeadQuarters(cricketBoardObject.getHeadQuarters());
            cricketBoard.setEstablishedYear(cricketBoardObject.getEstablishedYear());
            result = cricketBoardRepository.save(cricketBoard);
            return convertResultObjectOfCricketBoard(result);
        }
        else{
            result = cricketBoardRepository.save(cricketBoardObject);
            return convertResultObjectOfCricketBoard(result);
        }

    }

    @Override
    public CricketBoard getCricketBoard(Long id) {
        CricketBoard cricketBoard = cricketBoardRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No Cricket Board found with this id: " + id)
        );
        if(cricketBoard.getStatus().equals(Status.INACTIVE)){
            throw new RecordNotFoundException("No Cricket Board found with this id: " + id);
        }
        return cricketBoard;
    }

    @Override
    public List<CricketBoard> getAllCricketBoards() {
        return cricketBoardRepository.findCricketBoardByStatus(Status.ACTIVE);
    }

    @Override
    public CricketBoard updateCricketBoard(Long id, CricketBoard cricketBoardObject) {
        CricketBoard existingCricketBoard = cricketBoardRepository.findById(id).
                orElseThrow(() -> new RecordNotFoundException("Record not found with id: " + id));
        if(existingCricketBoard.getStatus().equals(Status.INACTIVE)){
            throw new RecordNotFoundException("No active cricket boards");
        }
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
