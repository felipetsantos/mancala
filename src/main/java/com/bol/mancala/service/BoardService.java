package com.bol.mancala.service;

import com.bol.mancala.model.Board;
import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;

import java.util.List;

public interface BoardService {

    Board endMatch(long matchId, long playerId);

    Board sowsStones(long matchId, long playerId, byte pitPosition);

    Board getBoard(long matchId);

}
