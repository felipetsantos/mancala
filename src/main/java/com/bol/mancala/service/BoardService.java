package com.bol.mancala.service;

import com.bol.mancala.model.Board;

public interface BoardService {

    Board endMatch(long matchId, long playerId);

    Board sowsStones(long matchId, long playerId, byte pitPosition);

    Board getBoard(long matchId);

}
