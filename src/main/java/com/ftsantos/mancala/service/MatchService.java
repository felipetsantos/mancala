package com.ftsantos.mancala.service;

import com.ftsantos.mancala.model.Match;
import com.ftsantos.mancala.model.Player;

import java.util.List;

public interface MatchService {

    /**
     * Get match by Id
     * @param id
     * @return
     */
    Match getMatchById(long id);

    /**
     * create a new match
     * @param match
     * @return
     */
    Match createMatch(Match match);

    /**
     * Update a existent match
     * @param match
     * @return
     */
    Match updateMatch(Match match);

    /**
     * Get match by status
     * @param status
     * @return
     */
    List<Match> getMatchesByStatus(Match.Status status);

    /**
     * Start a match with one player
     * the match will be in the status Match.Status.WAITING_SECOND_PLAYER and with the player 1 pits in the default state
     * @param playerId
     * @return
     */
    Match startMatch(Long playerId);

    /**
     * Join a player to a match in the status Match.Status.WAITING_SECOND_PLAYER
     * it also will create the pits for the second player in the default state
     * @param playerId
     * @param matchId
     * @return
     */
    Match joinToMatch(Long playerId, Long matchId);

    /**
     * Get matches in the status Match.Status.WAITING_SECOND_PLAYER
     *
     * @param player
     * @return
     */
    List<Match> getMatchesWaitingSecondPlayer(Player player);

    /**
     * Abandon a match, it will change the match status to Match.Status.ABANDON
     * @param matchId
     * @param playerId
     * @return
     */
    Match endMatch(long matchId, long playerId);
}
