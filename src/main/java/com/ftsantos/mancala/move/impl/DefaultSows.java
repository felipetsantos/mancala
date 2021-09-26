package com.ftsantos.mancala.move.impl;

import com.ftsantos.mancala.exception.BadRequestException;
import com.ftsantos.mancala.model.Board;
import com.ftsantos.mancala.model.Match;
import com.ftsantos.mancala.model.Pit;
import com.ftsantos.mancala.model.Player;
import com.ftsantos.mancala.move.Sows;

import java.util.Collections;
import java.util.List;

public class DefaultSows implements Sows {
    /**
     * mancala board
     */
    private Board board;

    /**
     * Board
     *
     * @param board
     */
    public DefaultSows(Board board) {
        this.board = board;
    }

    /**
     * Executes the sows move
     *
     * @param pitPosition
     * @param playerId
     * @return
     */
    @Override
    public Board execute(byte pitPosition, long playerId) {

        Player opponentPlayer = board.getOpponentPlayer(playerId);

        // split pits by players and sort it
        List<Pit> playerPits = board.getPlayerPits(playerId);
        Collections.sort(playerPits, new Pit.CompareByPosition());
        List<Pit> opponentPits = board.getPlayerPits(opponentPlayer.getId());
        Collections.sort(opponentPits, new Pit.CompareByPosition());

        // move stones to the right
        MoveStonesToRightResult moveAllStonesToRightResult = this.moveAllStonesToRight(pitPosition, playerPits, opponentPits);

        Pit pitWhereLastStoneLanded = moveAllStonesToRightResult.getPitWhereLastStoneLanded();
        byte pitStonesBeforeStoneLand = moveAllStonesToRightResult.getPitStonesBeforeStoneLand();

        // capturing rule
        if (this.canCaptureStones(pitWhereLastStoneLanded, playerId, pitStonesBeforeStoneLand)) {
            this.capturePitStones(pitWhereLastStoneLanded, playerPits, opponentPits);
        }

        // next player turn rule
        Long nextTurnPlayerId = opponentPlayer.getId();
        if (this.hasPlayerAnotherTurn(pitWhereLastStoneLanded)) {
            // last stone was sown in the player large pit, he has another turn
            nextTurnPlayerId = playerId;
        }

        board.setTurnPlayerId(nextTurnPlayerId);

        // game over rule
        if (this.isGameOver(playerPits, opponentPits)) {
            this.completeGame(playerPits, opponentPits);
        }
        return board;
    }

    /**
     * Move pit stones to the right
     *
     * @param pitPosition
     * @param playerPits
     * @param opponentPits
     * @return
     */
    private MoveStonesToRightResult moveAllStonesToRight(byte pitPosition, List<Pit> playerPits, List<Pit> opponentPits) {
        Pit selectedPit = playerPits.stream()
                .filter(pit -> pit.getPosition() == pitPosition)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid pit position"));
        Long playerId = selectedPit.getPlayer().getId();
        byte numberOfStoneToSows = selectedPit.getStonesCount();
        selectedPit.setStonesCount((byte) 0);
        List<Pit> pitsToSows = playerPits;
        byte postion = (byte)(pitPosition+1);
        Pit currentPit = null;
        byte currentPitStonesBeforeStoneLand = 0;
        while (numberOfStoneToSows > 0) {
            currentPit = pitsToSows.get(postion);
            if (currentPit.getPlayer().getId() != playerId && currentPit.getType() == Pit.Type.LARGE) {
                // skip opponent's large pit
                postion = 0;
                pitsToSows = playerPits;
                continue;
            }
            currentPitStonesBeforeStoneLand = currentPit.getStonesCount();
            currentPit.setStonesCount((byte) (currentPitStonesBeforeStoneLand + 1));
            numberOfStoneToSows--;
            postion++;
            if (postion >= pitsToSows.size()) {
                postion = 0;
                pitsToSows = currentPit.getPlayer().getId() == playerId ? opponentPits : playerPits;
            }
        }
        return new MoveStonesToRightResult(currentPit, currentPitStonesBeforeStoneLand);
    }

    /**
     * complete the game,
     * move remaining stones to the large pit, change board status to complete and set the winner
     *
     * @param playerPits
     * @param opponentPits
     */
    private void completeGame(List<Pit> playerPits, List<Pit> opponentPits) {
        board.setStatus(Match.Status.COMPLETED);
        this.moveRemainingStonesToLargePit(playerPits);
        this.moveRemainingStonesToLargePit(opponentPits);
        this.setWinner(playerPits, opponentPits);
    }

    /**
     * Set the board winner or if it was a draw
     *
     * @param playerPits
     * @param opponentPits
     */
    private void setWinner(List<Pit> playerPits, List<Pit> opponentPits) {
        Pit playerLargePit = this.getLargePit(playerPits);
        Pit opponentLargePit = this.getLargePit(opponentPits);
        if (playerLargePit.getStonesCount() > opponentLargePit.getStonesCount()) {
            board.setWinnerId(playerLargePit.getPlayer().getId());
        } else if (playerLargePit.getStonesCount() < opponentLargePit.getStonesCount()) {
            board.setWinnerId(opponentLargePit.getPlayer().getId());
        } else {
            board.setDraw(true);
        }
    }

    /**
     * get large pit for a player's pits
     *
     * @param playerPits
     * @return large pit
     */
    private Pit getLargePit(List<Pit> playerPits) {
        return playerPits.stream()
                .filter(pit -> pit.getType() == Pit.Type.LARGE)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Player does not have a large pit"));
    }

    /**
     * move all the stones from the player's pits to the player's large pit
     *
     * @param playerPits
     */
    private void moveRemainingStonesToLargePit(List<Pit> playerPits) {
        Pit largePit = this.getLargePit(playerPits);
        playerPits.stream()
                .filter(pit -> pit.getType() == Pit.Type.NORMAL)
                .forEach(
                pit -> {
                    largePit.setStonesCount((byte) (largePit.getStonesCount() + pit.getStonesCount()));
                    pit.setStonesCount((byte)0);
                });
    }

    /**
     * verify if the current palyer will have another turn
     *
     * @param lastSownPit
     * @return
     */
    private boolean hasPlayerAnotherTurn(Pit lastSownPit) {
        return lastSownPit.getType() == Pit.Type.LARGE;
    }

    /**
     * capture all the stones from the last sown pit and the opponent's pit stones in the same position
     *
     * @param lastSownPit
     * @param playerPits
     * @param opponentPits
     */
    private void capturePitStones(Pit lastSownPit, List<Pit> playerPits, List<Pit> opponentPits) {
        byte normalPitsTotal = (byte)(opponentPits.size()-2);
        Pit opponentPit = opponentPits.get(normalPitsTotal- lastSownPit.getPosition());
        Pit playerLargePit = playerPits.stream()
                .filter(pit -> pit.getType() == Pit.Type.LARGE)
                .findFirst()
                .orElse(null);
        playerLargePit.setStonesCount(
                (byte) (playerLargePit.getStonesCount() + opponentPit.getStonesCount() + lastSownPit.getStonesCount())
        );
        lastSownPit.setStonesCount((byte) 0);
        opponentPit.setStonesCount((byte) 0);
    }

    /**
     * verify if the stones should be captured
     *
     * @param lastSownPit
     * @param playerId
     * @param lastPitStonesCountBeforeSows
     * @return
     */
    private boolean canCaptureStones(Pit lastSownPit, long playerId, byte lastPitStonesCountBeforeSows) {
        return lastSownPit.getType() != Pit.Type.LARGE &&
                lastSownPit.getPlayer().getId() == playerId &&
                lastPitStonesCountBeforeSows == 0;
    }

    /**
     * verify if the game ends
     *
     * @param playerPits
     * @param opponentPits
     * @return
     */
    private boolean isGameOver(List<Pit> playerPits, List<Pit> opponentPits) {
        return this.sumPitsStonesByType(playerPits, Pit.Type.NORMAL) == 0 ||
                this.sumPitsStonesByType(opponentPits, Pit.Type.NORMAL) == 0;
    }

    /**
     * Sum pits stones by pit type
     *
     * @param pits
     * @param type
     * @return
     */
    private byte sumPitsStonesByType(List<Pit> pits, Pit.Type type) {
        return pits.stream()
                .filter(pit -> pit.getType() == type)
                .map(pit -> pit.getStonesCount())
                .reduce((byte) 0, (subtotal, stonesCount) -> (byte) (subtotal + stonesCount));
    }

    /**
     * Result after move stones to the right
     */
    public static class MoveStonesToRightResult {
        private Pit pitWhereLastStoneLanded;
        private byte pitStonesBeforeStoneLand;

        public MoveStonesToRightResult(Pit pitWhereLastStoneLanded, byte pitStonesBeforeLastStoneLand) {
            this.pitWhereLastStoneLanded = pitWhereLastStoneLanded;
            this.pitStonesBeforeStoneLand = pitStonesBeforeLastStoneLand;
        }

        public Pit getPitWhereLastStoneLanded() {
            return pitWhereLastStoneLanded;
        }

        public byte getPitStonesBeforeStoneLand() {
            return pitStonesBeforeStoneLand;
        }
    }
}
