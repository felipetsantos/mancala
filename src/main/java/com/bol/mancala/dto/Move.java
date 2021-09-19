package com.bol.mancala.dto;

public class Move {

    /**
     * player id
     */
    private long playerId;

    /**
     * position of the pit selected
     */
    private byte positionPitSelected;


    public Move(long playerId, byte positionPitSelected) {

        this.playerId = playerId;
        this.positionPitSelected = positionPitSelected;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public byte getPositionPitSelected() {
        return positionPitSelected;
    }

    public void setPositionPitSelected(byte positionPitSelected) {
        this.positionPitSelected = positionPitSelected;
    }
}
