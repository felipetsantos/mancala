package com.bol.mancala.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Stream;

@Entity
public class Pit implements Serializable {

    private static final long serialVersionUID = -4518411290759872816L;

    /**
     * Pit id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * type of pit
     */
    private Type type;

    /**
     * Number of stones in the pit
     */
    private byte stonesCount;

    /**
     * Pit position, from left to right
     */
    private byte position;

    /**
     * Pit's match
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    @JsonBackReference
    private Match match;

    /**
     * Pit's player
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public Pit() {
    }

    public Pit(long id, Type type, byte stonesCount, byte order, Match match, Player player) {
        this.id = id;
        this.type = type;
        this.stonesCount = stonesCount;
        this.position = order;
        this.match = match;
        this.player = player;
    }

    public boolean isEmpty() {
        return this.stonesCount == 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public byte getStonesCount() {
        return stonesCount;
    }

    public void setStonesCount(byte stonesCount) {
        this.stonesCount = stonesCount;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Pit builder
     */
    public static class Builder {
        private long id;
        private Type type;
        private byte stonesCount;
        private byte position;
        private Match match;
        private Player player;


        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setStonesCount(byte stonesCount) {
            this.stonesCount = stonesCount;
            return this;
        }

        public Builder setPosition(byte position) {
            this.position = position;
            return this;
        }

        public Builder setMatch(Match match) {
            this.match = match;
            return this;
        }

        public Builder setPlayer(Player player) {
            this.player = player;
            return this;
        }

        public Pit build() {
            return new Pit(this.id, this.type, this.stonesCount, this.position, this.match, this.player);
        }
    }

    /**
     * Pit Types
     */
    public enum Type {
        NORMAL((byte) 1),
        LARGE((byte) 2);

        private byte id;

        Type(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }
    }

    /**
     * Convert Type
     */
    @Converter(autoApply = true)
    public static class TypeConverter implements AttributeConverter<Type, Byte> {

        @Override
        public Byte convertToDatabaseColumn(Type type) {
            return type.getId();
        }

        @Override
        public Type convertToEntityAttribute(Byte typeValue) {
            return Stream.of(Type.values())
                    .filter(status -> status.getId() == typeValue)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public static class CompareByPosition implements Comparator<Pit> {

        @Override
        public int compare(Pit pit1, Pit pit2) {
            if (pit1.getPosition() > pit2.getPosition()) {
                return 1;
            } else if (pit1.getPosition() < pit2.getPosition()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
