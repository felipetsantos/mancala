package com.ftsantos.mancala.repository;

import com.ftsantos.mancala.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitRepository extends JpaRepository<Pit, Long> {
    
    List<Pit> findByMatchId(Long id);
}
