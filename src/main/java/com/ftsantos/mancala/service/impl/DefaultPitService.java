package com.ftsantos.mancala.service.impl;

import com.ftsantos.mancala.model.Pit;
import com.ftsantos.mancala.repository.PitRepository;
import com.ftsantos.mancala.service.PitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPitService implements PitService {

    private PitRepository pitRepository;

    @Autowired
    public DefaultPitService(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }

    /**
     * Get pits by match id
     * @param matchId
     * @return
     */
    @Override
    public List<Pit> getPitsByMatchId(Long matchId) {
        return this.pitRepository.findByMatchId(matchId);
    }

    /**
     * Create new pits
     * @param pits
     * @return
     */
    @Override
    public List<Pit> createPits(List<Pit> pits) {
        return this.pitRepository.saveAll(pits);
    }

}
