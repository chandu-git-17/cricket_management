    package com.example.cricketmanagement.controller;

    import com.example.cricketmanagement.dto.CricketBoardCreationDTO;
    import com.example.cricketmanagement.model.CricketBoard;
    import com.example.cricketmanagement.service.CricketBoardInterface;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/cricketboard")
    public class CricketBoardController {

        public final CricketBoardInterface cricketBoardService;

        @PostMapping()
        public CricketBoardCreationDTO createCricketBoard(@RequestBody CricketBoard cricketBoard){
            return cricketBoardService.createCricketBoard(cricketBoard);
        }

        @GetMapping("/{id}")
        public CricketBoard getCricketBoard(@PathVariable Long id){
            return cricketBoardService.getCricketBoard(id);
        }

        @GetMapping()
        public List<CricketBoard> getAllCricketBoards(){
            return cricketBoardService.getAllCricketBoards();
        }

        @PatchMapping("/{id}")
        public CricketBoard updateCricketBoard(@PathVariable Long id, @RequestBody CricketBoard cricketBoard){
            return cricketBoardService.updateCricketBoard(id, cricketBoard);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteCricketBoard(@PathVariable Long id){
            return cricketBoardService.deleteCricketBoard(id);
        }
    }
