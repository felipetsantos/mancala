package com.ftsantos.mancala.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 294843499898792811L;

    /**
     * Player id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Player name
     */
    private String name;

    /**
     * unique player user name
     */
    private String username;

    /**
     * date and time when the player was created
     */
    private LocalDateTime createdAt;

    /**
     *
     */
    @ManyToMany(mappedBy = "players")
    private Set<Match> matches = new HashSet<>();

    public Player() {
    }

    /**
     * Create new player
     *
     * @param id
     * @param name
     * @param username
     * @param createdAt
     */
    public Player(Long id, String name, String username, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(username, player.username) && Objects.equals(createdAt, player.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String username;
        private LocalDateTime createdAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Player build() {
            return new Player(this.id, this.name, this.username, this.createdAt);
        }
    }
}
