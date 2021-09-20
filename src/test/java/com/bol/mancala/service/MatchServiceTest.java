package com.bol.mancala.service;


import com.bol.mancala.model.Match;
import com.bol.mancala.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerService playerService;

    @Test
    @DisplayName("When a match creation is requested to the service then it is persisted")
    public void createMatch(){
        LocalDateTime startedAt = LocalDateTime.of(2021, 9, 14, 7, 16, 0, 0);
        Match match = Match.builder()
                .setStartedAt(startedAt)
                .setStatus(Match.Status.WAITING_PLAYERS)
                .build();
        Match match2 = matchService.createMatch(match);
        Assertions.assertTrue(match2.getId() > 0);
        assertEquals(startedAt, match2.getStartedAt());
        assertEquals(Match.Status.WAITING_PLAYERS, match2.getStatus());
    }

    @Test
    @DisplayName("When a match creation with players is requested to the service then match and players relation is persisted")
    public void createMatchWithPlayers(){
        LocalDateTime startedAt = LocalDateTime.of(2021, 9, 14, 7, 16, 0, 0);
        Player player = Player.builder().setId(3l)
                .setName("Player 1")
                .setUsername("player1Match")
                .setCreatedAt(startedAt)
                .build();
        Player player2 = Player.builder().setId(4l)
                .setName("Player 2")
                .setUsername("player2Match")
                .setCreatedAt(startedAt)
                .build();
        Set<Player> players = new HashSet<>();
        player = this.playerService.createPlayer(player);
        player2 = this.playerService.createPlayer(player2);
        players.add(player);
        players.add(player2);

        Match match = Match.builder()
                .setStartedAt(startedAt)
                .setStatus(Match.Status.IN_PROGRESS)
                .setPlayers(players)
                .build();
        Match savedMatch = this.matchService.createMatch(match);
        Match matchFound = this.matchService.getMatchById(savedMatch.getId());
        List<Player> playersFound = matchFound.getPlayers().stream().collect(Collectors.toList());
        assertEquals(playersFound.get(0).getId(), player.getId());
        assertEquals(playersFound.get(1).getId(), player2.getId());
        assertEquals(Match.Status.IN_PROGRESS, matchFound.getStatus());
    }



}