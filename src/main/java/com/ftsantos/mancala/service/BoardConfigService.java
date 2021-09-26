package com.ftsantos.mancala.service;

import com.ftsantos.mancala.model.Match;
import com.ftsantos.mancala.model.Pit;
import com.ftsantos.mancala.model.Player;

import java.util.List;

public interface BoardConfigService {

    /**
     * Get the default number of stones per normal pit
     * @return
     */
    byte getPitInitialStonesCount();

    /**
     * return the default pits for a player with the default configuration
     * @param player
     * @param match
     * @return
     */
    List<Pit> getPlayerPitsInitialConfig(Player player, Match match);
}
