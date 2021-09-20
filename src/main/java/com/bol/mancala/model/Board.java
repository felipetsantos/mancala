package com.bol.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board implements Serializable {

    private static final long serialVersionUID = -2043308928518040398L;

    private long matchId;

    private List<Player> players;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Pit> pits;

    private Long turnPlayerId;

    private Match.Status status;

    private Long winnerId = null;

    private boolean draw = false;

    public Board(long matchId, List<Player> players, List<Pit> pits, Long turnPlayerId, Match.Status status) {
        this.players = players;
        this.pits = pits;
        this.turnPlayerId = turnPlayerId;
        this.status = status;
        this.matchId = matchId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Pit> getPits() {
        return pits;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }

    public Long getTurnPlayerId() {
        return turnPlayerId;
    }

    public void setTurnPlayerId(Long turnPlayerId) {
        this.turnPlayerId = turnPlayerId;
    }

    public Match.Status getStatus() {
        return status;
    }

    public void setStatus(Match.Status status) {
        this.status = status;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public Optional<Pit> getPlayerPitByPosition(long playerId, byte position) {
        return this.getPlayerPitsStream(playerId).filter(pit -> pit.getPosition() == position).findFirst();
    }

    public List<Pit> getPlayerPits(long playerId) {
        return getPlayerPitsStream(playerId)
                .collect(Collectors.toList());
    }

    public Pit getPitByPlayerAndPosition(long playerId, byte position) {
        return getPlayerPitsStream(playerId)
                .filter(pit -> pit.getPosition() == position)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid position"));
    }

    public Player getOpponentPlayer(long currentPlayerId) {
        return players
                .stream()
                .filter(player -> player.getId() != currentPlayerId)
                .findFirst()
                .orElse(null);
    }

    private Stream<Pit> getPlayerPitsStream(long playerId) {
        return pits.stream().filter(pit -> pit.getPlayer().getId() == playerId);
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
