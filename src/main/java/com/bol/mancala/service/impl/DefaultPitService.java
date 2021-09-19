package com.bol.mancala.service.impl;

import com.bol.mancala.model.Pit;
import com.bol.mancala.repository.PitRepository;
import com.bol.mancala.service.PitService;
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

    @Override
    public List<Pit> getPitsByMatchId(Long matchId) {
        return this.pitRepository.findByMatchId(matchId);
    }

    @Override
    public List<Pit> createPits(List<Pit> pits) {
        return this.pitRepository.saveAll(pits);
    }

}
