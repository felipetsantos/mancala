package com.bol.mancala.controller.api;

import com.bol.mancala.dto.StartMatch;
import com.bol.mancala.model.Match;
import com.bol.mancala.model.Player;
import com.bol.mancala.service.MatchService;
import com.bol.mancala.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("start/{playerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Match create(@PathVariable Long playerId) {
        return this.matchService.startMatch(playerId);
    }

    @PutMapping("{matchId}/join/{playerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Match join(@PathVariable Long matchId, @PathVariable Long playerId) {
        return this.matchService.joinToMatch(playerId, matchId);
    }
}