package com.bol.mancala.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "match")
public class Match implements Serializable {

    private static final long serialVersionUID = 3038751850681298084L;

    /**
     * Match id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date time when the match started
     */
    private LocalDateTime startedAt;

    /**
     * Date time when the matched ended
     */
    private LocalDateTime endedAt;

    /**
     * Match status
     */
    private Status status;

    /**
     * turn player id
     */
    private Long turnPlayerId;

    /**
     * turn player id
     */
    private Long winnerPlayerId;

    /**
     * no winner, match end draw
     */
    private boolean draw;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "match_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> players = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match")
    @JsonManagedReference
    private Set<Pit> pits = new HashSet<>();

    public Match() {
    }

    public Match(Long id, LocalDateTime startedAt, Status status) {
        this.id = id;
        this.startedAt = startedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Long getTurnPlayerId() {
        return turnPlayerId;
    }

    public void setTurnPlayerId(Long turnPlayerId) {
        this.turnPlayerId = turnPlayerId;
    }

    public void setPits(Set<Pit> pits) {
        this.pits = pits;
    }

    public Long getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(Long winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public Set<Pit> getPits() {
        return pits;
    }

    public boolean isPlayerInTheMatch(Long playerId) {
        Set<Player> players = this.getPlayers();
        if (players.isEmpty()) {
            return false;
        }
        return players.stream().filter(player -> player.getId() == playerId).findFirst().isPresent();
    }

    public List<Pit> getPlayerPits(Long playerId) {
        return this.getPits().stream().filter(pit -> pit.getPlayer().getId() == playerId).collect(Collectors.toList());
    }

    public enum Status {
        WAITING_PLAYERS((byte) 1),
        WAITING_SECOND_PLAYER((byte) 2),
        IN_PROGRESS((byte) 3),
        ABANDONED((byte) 4),
        COMPLETED((byte) 5);

        private byte id;

        Status(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }
    }

    @Converter(autoApply = true)
    public static class StatusConverter implements AttributeConverter<Status, Byte> {

        @Override
        public Byte convertToDatabaseColumn(Status status) {
            return status.getId();
        }

        @Override
        public Status convertToEntityAttribute(Byte statusValue) {
            return Stream.of(Status.values())
                    .filter(status -> status.getId() == statusValue)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Status status;
        private Set<Player> players = new HashSet<>();


        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStartedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public Builder setEndedAt(LocalDateTime endedAt) {
            this.endedAt = endedAt;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setPlayers(Set<Player> players) {
            this.players = players;
            return this;
        }

        public Builder addPlayer(Player player) {
            this.players.add(player);
            return this;
        }


        public Match build() {
            Match match = new Match(this.id, this.startedAt, this.status);
            match.setEndedAt(endedAt);
            match.setPlayers(this.players);
            return match;
        }
    }
}
