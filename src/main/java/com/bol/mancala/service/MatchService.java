package com.bol.mancala.service;

import com.bol.mancala.model.Match;
import com.bol.mancala.model.Player;

import java.util.List;

public interface MatchService {

    Match getMatchById(long id);

    Match createMatch(Match match);

    Match updateMatch(Match match);

    List<Match> getMatchesByStatus(Match.Status status);

    Match startMatch(Long playerId);

    Match joinToMatch(Long playerId, Long matchId);

    List<Match> getMatchesWaitingSecondPlayer(Player player);
}
