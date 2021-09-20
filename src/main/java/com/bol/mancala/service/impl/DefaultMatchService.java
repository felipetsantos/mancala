package com.bol.mancala.service.impl;

import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.exception.ElementNotFoundException;
import com.bol.mancala.exception.NotAuthorizedException;
import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;
import com.bol.mancala.repository.MatchRepository;
import com.bol.mancala.service.BoardConfigService;
import com.bol.mancala.service.MatchService;
import com.bol.mancala.service.PitService;
import com.bol.mancala.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultMatchService implements MatchService {

    private MatchRepository matchRepository;
    private BoardConfigService boardConfigService;
    private PitService pitService;
    private PlayerService playerService;

    /**
     * @param matchRepository
     * @param boardConfigService
     * @param pitService
     * @param playerService
     */
    @Autowired
    public DefaultMatchService(
            MatchRepository matchRepository, BoardConfigService boardConfigService, PitService pitService, PlayerService playerService
    ) {
        this.matchRepository = matchRepository;
        this.boardConfigService = boardConfigService;
        this.pitService = pitService;
        this.playerService = playerService;
    }

    /**
     * find match by id
     *
     * @param id
     * @return
     */
    @Override
    public Match getMatchById(long id) {
        return this.matchRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Match %d not found.", id)));
    }

    /**
     * Create new match
     *
     * @param match
     * @return
     */
    @Override
    public Match createMatch(Match match) {
        return this.matchRepository.save(match);
    }

    /**
     * Update a existent match
     *
     * @param match
     * @return
     */
    @Override
    public Match updateMatch(Match match) {
        // fetch match to unsure that it exist, before try to update it
        Match existentMatch = this.getMatchById(match.getId());
        return this.matchRepository.save(match);
    }

    /**
     * Get match by status
     *
     * @param status
     * @return
     */
    @Override
    public List<Match> getMatchesByStatus(Match.Status status) {
        return this.matchRepository.findAllByStatus(status);

    }

    /**
     * Start a match with one player
     * the match will be in the status Match.Status.WAITING_SECOND_PLAYER and with the player 1 pits in the default state
     *
     * @param playerId
     * @return
     */
    @Override
    public Match startMatch(Long playerId) {
        Player player = this.playerService.getPlayerById(playerId);
        Match match = Match.builder()
                .addPlayer(player)
                .setStatus(Match.Status.WAITING_SECOND_PLAYER)
                .setStartedAt(LocalDateTime.now())
                .build();
        match = this.createMatch(match);
        match.setTurnPlayerId(player.getId());
        List<Pit> pits = this.boardConfigService.getPlayerPitsInitialConfig(player, match);
        this.pitService.createPits(pits);
        return match;
    }

    /**
     * Join a player to a match in the status Match.Status.WAITING_SECOND_PLAYER
     * it also will create the pits for the second player in the default state
     *
     * @param playerId
     * @param matchId
     * @return
     */
    @Override
    public Match joinToMatch(Long playerId, Long matchId) {
        Player player = this.playerService.getPlayerById(playerId);
        Match match = this.getMatchById(matchId);
        if (match.getPlayers().size() >= 2) {
            throw new BadRequestException("Only two player can join to the match");
        }
        match.getPlayers().add(player);
        Player player1 = match.getPlayers().stream().collect(Collectors.toList()).get(0);
        match.setTurnPlayerId(player1.getId());
        match.setStatus(Match.Status.IN_PROGRESS);
        List<Pit> pits = this.boardConfigService.getPlayerPitsInitialConfig(player, match);
        this.pitService.createPits(pits);
        return this.updateMatch(match);
    }

    /**
     * Get matches in the status Match.Status.WAITING_SECOND_PLAYER
     *
     * @param player
     * @return
     */
    @Override
    public List<Match> getMatchesWaitingSecondPlayer(Player player) {
        return this.getMatchesByStatus(Match.Status.WAITING_SECOND_PLAYER)
                .stream()
                .filter(match -> !match.isPlayerInTheMatch(player.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Abandon a match, it will change the match status to Match.Status.ABANDON and set an ended_at date
     *
     * @param matchId
     * @param playerId
     * @return
     */
    @Override
    public Match endMatch(long matchId, long playerId) {
        Match match = this.getMatchById(matchId);
        this.validateMatchPlayer(match, playerId);
        match.setStatus(Match.Status.ABANDONED);
        match.setEndedAt(LocalDateTime.now());
        return this.updateMatch(match);
    }

    private void validateMatchPlayer(Match match, Long playerId) {
        if (!match.isPlayerInTheMatch(playerId)) {
            throw new NotAuthorizedException(String.format("Player %d is not in the match %d", playerId, match.getId()));
        }
    }
}
