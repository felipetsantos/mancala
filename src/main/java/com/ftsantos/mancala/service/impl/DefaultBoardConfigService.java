package com.ftsantos.mancala.service.impl;

import com.ftsantos.mancala.model.Match;
import com.ftsantos.mancala.model.Pit;
import com.ftsantos.mancala.model.Player;
import com.ftsantos.mancala.service.BoardConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class DefaultBoardConfigService implements BoardConfigService {


    private static final byte PLAYER_PITS_COUNT = 6;
    private static final byte INITIAL_STONES_PER_PIT = 6;

    /**
     * Get the default number of stones per normal pit
     * 6 stones
     *
     * @return
     */
    @Override
    public byte getPitInitialStonesCount() {
        return DefaultBoardConfigService.INITIAL_STONES_PER_PIT;
    }

    /**
     * return the default pits for a player with the default configuration
     * 6 normal pits
     * 1 large pit
     *
     * @param player
     * @param match
     * @return
     */
    @Override
    public List<Pit> getPlayerPitsInitialConfig(Player player, Match match) {
        List<Pit> pits = new ArrayList<>(DefaultBoardConfigService.PLAYER_PITS_COUNT);
        IntStream.range(0, DefaultBoardConfigService.PLAYER_PITS_COUNT).forEach((i) -> pits
                .add(
                        Pit.builder()
                                .setPosition((byte) i)
                                .setMatch(match)
                                .setPlayer(player)
                                .setStonesCount(this.getPitInitialStonesCount())
                                .setType(Pit.Type.NORMAL)
                                .build()
                )
        );
        pits.add(Pit.builder()
                .setPosition((byte) pits.size())
                .setMatch(match)
                .setPlayer(player)
                .setStonesCount((byte) 0)
                .setType(Pit.Type.LARGE)
                .build());
        return pits;
    }
}
