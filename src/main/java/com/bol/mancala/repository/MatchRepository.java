package com.bol.mancala.repository;

import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByStatus(Match.Status status);
}
