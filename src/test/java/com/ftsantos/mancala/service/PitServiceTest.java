package com.ftsantos.mancala.service;

import com.ftsantos.mancala.model.Match;
import com.ftsantos.mancala.model.Pit;
import com.ftsantos.mancala.model.Player;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PitServiceTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private BoardConfigService boardConfigService;

    @Autowired
    private PitService pitService;

    @Autowired
    private MatchService matchService;

    @Test
    @DisplayName("When a Pits Creation is requested to the service then it is persisted")
    public void createPits() {
        LocalDateTime startedAt = LocalDateTime.of(2021, 9, 14, 7, 16, 0, 0);
        Set<Player> players = new HashSet<>();
        Player player = this.playerService.getPlayerById(1l);
        Player player2 = this.playerService.getPlayerById(2l);
        players.add(player);
        players.add(player2);
        Match match = Match.builder()
                .setStartedAt(startedAt)
                .setStatus(Match.Status.IN_PROGRESS)
                .setPlayers(players)
                .build();
        Match match2 = Match.builder()
                .setStartedAt(startedAt)
                .setStatus(Match.Status.IN_PROGRESS)
                .setPlayers(players)
                .build();
        Match savedMatch = this.matchService.createMatch(match);
        Match savedMatch2 = this.matchService.createMatch(match2);
        List<Pit> player1Pits = this.boardConfigService.getPlayerPitsInitialConfig(player, savedMatch);
        List<Pit> player1PitsMatch2 = this.boardConfigService.getPlayerPitsInitialConfig(player, savedMatch2);
        List<Pit> player2Pits = this.boardConfigService.getPlayerPitsInitialConfig(player2, savedMatch);
        List<Pit> player2PitsMatch2 = this.boardConfigService.getPlayerPitsInitialConfig(player2, savedMatch2);
        this.pitService.createPits(player1Pits);
        this.pitService.createPits(player2Pits);
        this.pitService.createPits(player1PitsMatch2);
        this.pitService.createPits(player2PitsMatch2);
        List<Pit> savedPits = this.pitService.getPitsByMatchId(savedMatch.getId());
        List<Pit> savedPits2 = this.pitService.getPitsByMatchId(savedMatch2.getId());
        assertEquals(14, savedPits.size());
        assertEquals(14, savedPits2.size());
        final long playerId = player.getId();
        final long player2Id = player2.getId();
        List<Pit> player1NormalPits = savedPits.stream()
                .filter(pit -> pit.getType().equals(Pit.Type.NORMAL))
                .filter(pit -> pit.getPlayer().getId().equals(playerId))
                .collect(Collectors.toList());
        List<Pit> player2NormalPitsMatch2 = savedPits2.stream()
                .filter(pit -> pit.getType().equals(Pit.Type.NORMAL))
                .filter(pit -> pit.getPlayer().getId().equals(player2Id))
                .collect(Collectors.toList());
        List<Pit> playerNormalPitsMatch2 = savedPits2.stream()
                .filter(pit -> pit.getType().equals(Pit.Type.NORMAL))
                .filter(pit -> pit.getPlayer().getId().equals(playerId))
                .collect(Collectors.toList());
        assertEquals(6, player1NormalPits.get(0).getStonesCount());
        assertEquals(6, player2NormalPitsMatch2.get(1).getStonesCount());
        assertNotEquals(player1NormalPits.get(0).getId(), playerNormalPitsMatch2.get(0).getId());
    }
}