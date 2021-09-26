package com.ftsantos.mancala.repository;

import com.ftsantos.mancala.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    /**
     * Find player by username
     * @param username
     * @return
     */
    Player findByUsername(String username);
}
