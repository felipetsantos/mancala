package com.ftsantos.mancala.service;

import com.ftsantos.mancala.model.Pit;

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
