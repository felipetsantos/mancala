package com.bol.mancala.service.impl;

import com.bol.mancala.model.Board;
import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.exception.NotAuthorizedException;
import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;
import com.bol.mancala.move.Sows;
import com.bol.mancala.move.impl.DefaultSows;
import com.bol.mancala.service.BoardService;
import com.bol.mancala.service.MatchService;
import com.bol.mancala.service.PitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultBoardService implements BoardService {

    private MatchService matchService;

    private PitService pitService;

    /**
     * @param matchService
     * @param pitService
     */
    @Autowired
    public DefaultBoardService(MatchService matchService, PitService pitService) {
        this.matchService = matchService;
        this.pitService = pitService;
    }

    /**
     * Create a board based in the match
     *
     * @param match
     * @return
     */
    private Board buildBoard(Match match) {
        List<Player> players = match.getPlayers().stream().collect(Collectors.toList());
        List<Pit> pits = this.pitService.getPitsByMatchId(match.getId());
        return new Board(
                match.getId(),
                players,
                pits,
                match.getTurnPlayerId(),
                match.getStatus()
        );
    }

    /**
     * executes the sows move for a player and position
     *
     * @param matchId
     * @param playerId
     * @param pitPosition
     * @return
     */
    @Override
    public Board sowsStones(long matchId, long playerId, byte pitPosition) {
        Match match = this.matchService.getMatchById(matchId);
        Board board = this.buildBoard(match);
        this.validate(board, match, playerId, pitPosition);
        Sows moveSows = new DefaultSows(board);
        board = moveSows.execute(pitPosition, playerId);
        match.setTurnPlayerId(board.getTurnPlayerId());
        match.setStatus(board.getStatus());
        match.setDraw(board.isDraw());
        match.setWinnerPlayerId(board.getWinnerId());
        this.matchService.updateMatch(match);
        this.pitService.createPits(board.getPits());
        return board;
    }

    /**
     * return a board for a given match id
     *
     * @param matchId
     * @return
     */
    @Override
    public Board getBoard(long matchId) {
        Match match = this.matchService.getMatchById(matchId);
        return this.buildBoard(match);
    }

    /**
     * @param board
     * @param match
     * @param playerId
     * @param position
     */
    private void validate(Board board, Match match, long playerId, byte position) {
        this.validateMatchPlayer(match, playerId);
        this.validateMatchStatus(match, Match.Status.IN_PROGRESS);
        this.validatePlayerTurn(match, playerId);
        this.validatePitPosition(board, playerId, position);
    }

    /**
     * Validate if the selected pit position is a valid for the move
     *
     * @param board
     * @param playerId
     * @param position
     */
    private void validatePitPosition(Board board, long playerId, byte position) {
        List<Pit> pits = board.getPlayerPits(playerId);
        Pit pit = pits.stream().filter(p -> p.getPosition() == position)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format("Pit position %d is invalid", position)));
        if (pit.getStonesCount() == 0) {
            throw new BadRequestException(String.format("Pit %d is empty, invalid position", position));
        }
    }

    /**
     * Validate if is the player turn
     *
     * @param match
     * @param playerId
     */
    private void validatePlayerTurn(Match match, Long playerId) {
        if (match.getTurnPlayerId() != playerId) {
            throw new BadRequestException(String.format("Player %d is not in the turn", playerId));
        }
    }

    /**
     * Validate if the match is in the desired status
     *
     * @param match
     * @param status
     */
    private void validateMatchStatus(Match match, Match.Status status) {
        if (match.getStatus() != status) {
            throw new BadRequestException(String.format("Match %d is not in the status %s", match.getId(), status.toString()));
        }
    }

    /**
     * Validate if the player is in the match
     *
     * @param match
     * @param playerId
     */
    private void validateMatchPlayer(Match match, Long playerId) {
        if (!match.isPlayerInTheMatch(playerId)) {
            throw new NotAuthorizedException(String.format("Player %d is not in the match %d", playerId, match.getId()));
        }
    }
}
