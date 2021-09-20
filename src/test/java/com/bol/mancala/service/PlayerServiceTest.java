package com.bol.mancala.service;

import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.exception.ElementNotFoundException;
import com.bol.mancala.model.Player;
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
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Test
    @DisplayName("When a Player Creation is requested to the service then it is persisted")
    public void createPlayer(){
        LocalDateTime createdAt = LocalDateTime.of(2021, 9, 13, 10, 21, 0, 0);
        Player player = Player.builder()
                .setName("Player 1")
                .setUsername("createPlayer1")
                .setCreatedAt(createdAt)
                .build();
        Player player1 = playerService.createPlayer(player);
        assertTrue(player1.getId() > 0);
        assertTrue(player1.getName().equals("Player 1"));
        assertTrue(player1.getUsername().equals("createPlayer1"));
        assertEquals(createdAt, player1.getCreatedAt());
    }

    @Test
    @DisplayName("When a Player Creation with a duplicate user name is requested to the service then a BadRequestException is thrown")
    public void duplicatedUserName(){
        LocalDateTime createdAt = LocalDateTime.of(2021, 9, 13, 10, 21, 0, 0);
        Player player = Player.builder()
                .setName("Player 1")
                .setUsername("player1")
                .setCreatedAt(createdAt)
                .build();
        assertThrows(BadRequestException.class, () -> this.playerService.createPlayer(player) );
    }

    @Test
    @DisplayName("When a player is requested and the id is invalid then a ElementNotFoundException is thrown")
    public void elementNotExist(){
        assertThrows(ElementNotFoundException.class, () -> this.playerService.getPlayerById(10l) );
    }
}
