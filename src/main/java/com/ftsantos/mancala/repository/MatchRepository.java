package com.ftsantos.mancala.repository;

import com.ftsantos.mancala.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByStatus(Match.Status status);
}
