package com.bol.mancala.service;

import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;

import java.util.List;

public interface BoardConfigService {


    byte getPitInitialStonesCount();

    List<Pit> getPlayerPitsInitialConfig(Player player, Match match);
}
