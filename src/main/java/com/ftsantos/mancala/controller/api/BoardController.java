package com.ftsantos.mancala.controller.api;

import com.ftsantos.mancala.dto.Move;
import com.ftsantos.mancala.model.Board;
import com.ftsantos.mancala.service.BoardService;
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
