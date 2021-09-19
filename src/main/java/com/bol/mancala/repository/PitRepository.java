package com.bol.mancala.repository;

import com.bol.mancala.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PitRepository extends JpaRepository<Pit, Long> {
    
    List<Pit> findByMatchId(Long id);
}
