package com.ftsantos.mancala.move;

import com.ftsantos.mancala.model.Board;

public interface Sows {

    public Board execute(byte pitPosition, long playerId);
}
