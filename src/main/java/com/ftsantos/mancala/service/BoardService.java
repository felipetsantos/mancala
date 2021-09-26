package com.ftsantos.mancala.service;

import com.ftsantos.mancala.model.Board;

public interface BoardService {
    /**
     * executes the sows move for a player and position
     *
     * @param matchId
     * @param playerId
     * @param pitPosition
     * @return
     */
    Board sowsStones(long matchId, long playerId, byte pitPosition);

    /**
     * return a board for a given match id
     *
     * @param matchId
     * @return
     */
    Board getBoard(long matchId);

}
