package com.bol.mancala.service.impl;

import com.bol.mancala.exception.ElementNotFoundException;
import com.bol.mancala.model.Player;
import com.bol.mancala.service.PlayerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DefaultPlayerServiceTest {
    @Autowired
    private PlayerService playerService;

    @Test
    @DisplayName("create new player")
    public void createPlayer(){
        LocalDateTime createdAt = LocalDateTime.of(2021, 9, 13, 10, 21, 0, 0);
        Player player = Player.builder().setId(1l)
                .setName("Player 1")
                .setUsername("player1")
                .setCreatedAt(createdAt)
                .build();
        playerService.createNewPlayer(player);
        Player player1 = playerService.getPlayerById(1l);
        assertEquals(1l, player1.getId());
        assertTrue( player1.getName().equals("Player 1"));
        assertTrue( player1.getUsername().equals("player1"));
        assertEquals(createdAt, player1.getCreatedAt());
    }

    @Test
    @DisplayName("player not found")
    public void elementNotExist(){
        assertThrows(ElementNotFoundException.class, () -> this.playerService.getPlayerById(10l) );
    }
}
