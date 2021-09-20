package com.bol.mancala.service;

import com.bol.mancala.model.Player;

public interface PlayerService {

    /**
     * Get a player by Id
     * @param id
     * @return
     */
    Player getPlayerById(Long id);

    /**
     * Create a new player
     * @param player
     * @return
     */
    Player createPlayer(Player player);
}
