package com.bol.mancala.controller.api;

import com.bol.mancala.dto.Move;
import com.bol.mancala.dto.StartMatch;
import com.bol.mancala.model.Board;
import com.bol.mancala.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private BoardService boardService;

    @Autowired
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("{matchId}")
    @ResponseStatus(HttpStatus.OK)
    public Board get(@PathVariable Long matchId) {
        return this.boardService.getBoard(matchId);
    }

    @PutMapping("{matchId}/sows")
    @ResponseStatus(HttpStatus.OK)
    public Board sows(@PathVariable Long matchId, @RequestBody Move move) {
        return this.boardService.sowsStones(
                matchId, move.getPlayerId(), move.getPositionPitSelected()
        );
    }
}
