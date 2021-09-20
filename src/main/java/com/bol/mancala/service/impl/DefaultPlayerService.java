package com.bol.mancala.service.impl;

import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.exception.ElementNotFoundException;
import com.bol.mancala.model.Player;
import com.bol.mancala.repository.PlayerRepository;
import com.bol.mancala.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public DefaultPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Get player by id
     *
     * @param id
     * @return
     * @throws ElementNotFoundException
     */
    @Override
    public Player getPlayerById(Long id) {
        return this.playerRepository.findById(id)
                .orElseThrow(
                        () -> new ElementNotFoundException(String.format("Player %d not found", id))
                );
    }

    /**
     * Create a new player
     * @param player
     * @return
     */
    @Override
    public Player createPlayer(Player player) {
        Player duplicatedPlayer = this.playerRepository.findByUsername(player.getUsername());
        if (duplicatedPlayer != null) {
            throw new BadRequestException(String.format("Username \"%s\" already in use", player.getName()));
        }
        return playerRepository.save(player);
    }
}
