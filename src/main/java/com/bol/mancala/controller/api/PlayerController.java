package com.bol.mancala.controller.api;

import com.bol.mancala.model.Player;
import com.bol.mancala.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    /**
     * Player Service
     */
    private PlayerService playerService;

    /**
     * @param playerService
     */
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody Player player) {
        player = this.playerService.createPlayer(player);
        return player;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player get(@PathVariable Long id) {
        return this.playerService.getPlayerById(id);
    }

}
