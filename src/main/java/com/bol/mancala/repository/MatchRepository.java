package com.bol.mancala.repository;

import com.bol.mancala.model.Match;
import com.bol.mancala.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByStatus(Match.Status status);
}
