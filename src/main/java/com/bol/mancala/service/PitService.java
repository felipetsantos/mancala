package com.bol.mancala.service;

import com.bol.mancala.model.Pit;

import java.util.List;

public interface PitService {

    /**
     * Get pits by match id
     * @param matchId
     * @return
     */
     List<Pit> getPitsByMatchId(Long matchId);

    /**
     * Create new pits
     * @param pits
     * @return
     */
    List<Pit> createPits(List<Pit> pits);
}
