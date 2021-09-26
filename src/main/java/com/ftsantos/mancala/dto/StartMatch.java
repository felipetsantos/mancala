package com.ftsantos.mancala.dto;

public class StartMatch {

    /**
     * player id
     */
    private long playerId;


    public StartMatch(long playerId) {

        this.playerId = playerId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
