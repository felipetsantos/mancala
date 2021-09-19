package com.bol.mancala.move;

import com.bol.mancala.model.Board;

public interface Sows {

    public Board execute(byte pitPosition, long playerId);
}
