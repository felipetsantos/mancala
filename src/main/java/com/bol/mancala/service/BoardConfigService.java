package com.bol.mancala.service;

import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;

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
