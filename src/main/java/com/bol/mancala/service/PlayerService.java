package com.bol.mancala.service;

import com.bol.mancala.model.Player;

public interface PlayerService {

    Player getPlayerById(Long id);

    Player createNewPlayer(Player player);
}
