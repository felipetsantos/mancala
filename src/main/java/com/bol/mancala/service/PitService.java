package com.bol.mancala.service;

import com.bol.mancala.model.Pit;

import java.util.List;

public interface PitService {

    public List<Pit> getPitsByMatchId(Long matchId);

    public List<Pit> createPits(List<Pit> pits);
}
