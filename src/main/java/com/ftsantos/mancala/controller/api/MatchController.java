package com.ftsantos.mancala.controller.api;

import com.ftsantos.mancala.model.Match;
import com.ftsantos.mancala.service.MatchService;
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

    @PutMapping("{matchId}/end/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Match end(@PathVariable Long matchId, @PathVariable Long playerId) {
        return this.matchService.endMatch(matchId, playerId);
    }

    @PutMapping("{matchId}/join/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Match join(@PathVariable Long matchId, @PathVariable Long playerId) {
        return this.matchService.joinToMatch(playerId, matchId);
    }
}
