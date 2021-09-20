package com.bol.mancala.service;

import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.Match;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    /**
     * -- Initial board setup
     * ------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1     -------     6       6       6       6       6       6      -----
     * --  LARGE PIT |  0   |---------------------------------------------------|  0  | LARGE PIT
     * -- player2     ______      6      6       6       6       6       6       _____
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 1
     * Select Player 1 pit 0
     * <p>
     * Result:
     * -- Initial board setup
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1      ----        7       7       7       7       7       0     -----
     * --  LARGE PIT  |  1  |---------------------------------------------------|  0  | LARGE PIT
     * -- player2      ----        6      6       6       6       6       6      ____
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * Another turn player 1
     */
    @Test
    @DisplayName("When the last stone of sows landed in the current player's lager pit, the current player have other turn")
    public void testSowsRepeatTurn() {
        final long matchId = 1;
        final long player1 = 1;
        Board board = boardService.sowsStones(matchId, player1, (byte) 0);
        byte stonesPit0 = board.getPitByPlayerAndPosition(player1, (byte) 0).getStonesCount();
        byte stonesPit1 = board.getPitByPlayerAndPosition(player1, (byte) 1).getStonesCount();
        byte stonesPit6 = board.getPitByPlayerAndPosition(player1, (byte) 6).getStonesCount();
        assertEquals(0, stonesPit0);
        assertEquals(7, stonesPit1);
        assertEquals(1, stonesPit6);
        assertEquals(player1, board.getTurnPlayerId());
    }


    /**
     * -- Test sows stones of a pit that will land the last stone in the opponent's pits
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1      ------      7       7       7       7       7       0     -------
     * --  LARGE PIT  |  1  |---------------------------------------------------|  0  | LARGE PIT
     * -- player2     ------       6      6       6       6       6       6     ------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 1
     * Select Player 1 pit 1
     * <p>
     * Result:
     * -- Initial board setup
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1    --------      7       7       7       7       0       0   -------
     * --  LARGE PIT |  2   |--------------------------------------------------|  0  | LARGE PIT
     * -- player2    --------    7       7       6       6       6         0   ------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 2
     */
    @Test
    @DisplayName("When the amount of stones in the selected pit is bigger than the current player's pits to the right it should increment the number of stones in the opponents player's pits until the stones ends")
    public void testSowsLastStoneLandsInOpponentPits() {
        final long matchId = 2;
        final long player1 = 1;
        final long player2 = 2;
        Board board = boardService.sowsStones(matchId, player1, (byte) 1);
        byte stonesPit1 = board.getPitByPlayerAndPosition(player1, (byte) 1).getStonesCount();
        byte stonesLargePit6 = board.getPitByPlayerAndPosition(player1, (byte) 6).getStonesCount();
        byte player2StonesPit0 = board.getPitByPlayerAndPosition(player2, (byte) 0).getStonesCount();
        byte player2StonesPit1 = board.getPitByPlayerAndPosition(player2, (byte) 1).getStonesCount();
        assertEquals(0, stonesPit1);
        assertEquals(2, stonesLargePit6);
        assertEquals(7, player2StonesPit0);
        assertEquals(7, player2StonesPit1);
        assertEquals(player2, board.getTurnPlayerId());

    }


    /**
     * -- initial board
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1     -------      8       8       8       8       0       0    ------
     * --  LARGE PIT |  2   |--------------------------------------------------|  0  | LARGE PIT
     * -- player2    -------       7      7       6       6       6       6    ------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 1
     * Select Player 1 pit 5
     * the move also covers the scenario where the sows should skip the opponent's large pit
     * <p>
     * -- Initial board setup
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1     --------     0       8       8       8       0       0     -------
     * --  LARGE PIT |  11   |---------------------------------------------------|  0  | LARGE PIT
     * -- player2    --------      8      8       7       7       7        0     -------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 2
     */
    @Test
    @DisplayName("When the last stone of a sows landed in the current player's empty pit it should move the stones from the last pit and the opponent's pit in the same position to the player's large pit")
    public void testSowsSkipOpponentPitAndCaptureStones() {
        final long matchId = 3;
        final long player1 = 1;
        final long player2 = 2;
        Board board = boardService.sowsStones(matchId, player1, (byte) 5);
        byte player1StonesPit5 = board.getPitByPlayerAndPosition(player1, (byte) 5).getStonesCount();
        byte player1StonesPit0 = board.getPitByPlayerAndPosition(player1, (byte) 0).getStonesCount();
        byte player1StonesLargePit6 = board.getPitByPlayerAndPosition(player1, (byte) 6).getStonesCount();

        byte player2StonesPit0 = board.getPitByPlayerAndPosition(player2, (byte) 0).getStonesCount();
        byte player2StonesPit1 = board.getPitByPlayerAndPosition(player2, (byte) 1).getStonesCount();
        byte player2StonesPit2 = board.getPitByPlayerAndPosition(player2, (byte) 2).getStonesCount();
        byte player2StonesLargePit6 = board.getPitByPlayerAndPosition(player2, (byte) 6).getStonesCount();

        assertEquals(0, player1StonesPit5);
        assertEquals(0, player1StonesPit0);
        assertEquals(11, player1StonesLargePit6);

        assertEquals(8, player2StonesPit0);
        assertEquals(8, player2StonesPit1);
        assertEquals(7, player2StonesPit2);
        assertEquals(0, player2StonesLargePit6);
        assertEquals(player2, board.getTurnPlayerId());
    }

    /**
     * -- Initial board setup
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1     --------     0       8       8       8       0       0     -------
     * --  LARGE PIT |  11   |---------------------------------------------------|  0  | LARGE PIT
     * -- player2    --------      8      8       7       7       7        0     -------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 2
     * Select Player 2 pit 0
     * <p>
     * -- Initial board setup
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1     --------     0       8       8       8       1       1     -------
     * --  LARGE PIT |  11   |---------------------------------------------------|  1  | LARGE PIT
     * -- player2    --------      0      9       8       8       8        1     -------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 1
     */
    @Test
    @DisplayName("When the last stone of a sows landed in the opponent's empty pit it shouldn't capture the stones")
    public void testSowsLastStoneLandedInOpponentsEmptyPit() {
        final long matchId = 4;
        final long player1 = 1;
        final long player2 = 2;
        Board board = boardService.sowsStones(matchId, player2, (byte) 0);

        byte player2StonesPit0 = board.getPitByPlayerAndPosition(player2, (byte) 0).getStonesCount();
        byte player2StonesPit1 = board.getPitByPlayerAndPosition(player2, (byte) 1).getStonesCount();
        byte player2StonesLargePit6 = board.getPitByPlayerAndPosition(player2, (byte) 6).getStonesCount();

        byte player1StonesPit0 = board.getPitByPlayerAndPosition(player1, (byte) 0).getStonesCount();
        byte player1StonesPit1 = board.getPitByPlayerAndPosition(player1, (byte) 1).getStonesCount();
        byte player1StonesLargePit6 = board.getPitByPlayerAndPosition(player1, (byte) 6).getStonesCount();

        assertEquals(0, player2StonesPit0);
        assertEquals(9, player2StonesPit1);
        assertEquals(1, player2StonesLargePit6);

        assertEquals(1, player1StonesPit0);
        assertEquals(1, player1StonesPit1);
        assertEquals(11, player1StonesLargePit6);
        assertEquals(player1, board.getTurnPlayerId());
    }

    /**
     * --- Initial board setup
     * ----------------------- |Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * --- player1    --------    10      0       0      13       0       0       -------
     * ---  LARGE PIT |  29   |---------------------------------------------------|  19  | LARGE PIT
     * --- player2    --------    0      0        0      0       1        0       -------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 2
     * Select Player 2 pit 0
     */
    @Test
    @DisplayName("When a sows request is is not valid a BadRequestException should be thrown")
    public void invalidPositionThrowsBadRequestException() {
        final long matchId = 6;
        final long player2 = 2;
        assertThrows(BadRequestException.class, () -> boardService.sowsStones(matchId, player2, (byte) 0));
    }

    /**
     * --- Initial board setup
     * ----------------------- |Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * --- player1    --------    10      0       0      13       0       0       -------
     * ---  LARGE PIT |  29   |---------------------------------------------------|  19  | LARGE PIT
     * --- player2    --------    0      0        0      0       1        0       -------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 2
     * Select Player 2 pit 4
     * <p>
     * -- Initial board setup
     * -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
     * -- player1     --------    0      0       0       0       0       0        -------
     * --  LARGE PIT |  52   |---------------------------------------------------|  20  | LARGE PIT
     * -- player2    --------     0      0        0      0       0        0      -------
     * ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
     * turn player 1
     */
    @Test
    @DisplayName("When a player stones finish it should complete the match and give the winner")
    public void player1Won() {
        final long matchId = 6;
        final long player1 = 1;
        final long player2 = 2;
        Board board = boardService.sowsStones(matchId, player2, (byte) 4);

        byte player2StonesPit4 = board.getPitByPlayerAndPosition(player2, (byte) 4).getStonesCount();
        byte player2StonesPit5 = board.getPitByPlayerAndPosition(player2, (byte) 5).getStonesCount();
        byte player2StonesLargePit6 = board.getPitByPlayerAndPosition(player2, (byte) 6).getStonesCount();

        byte player1StonesPit2 = board.getPitByPlayerAndPosition(player1, (byte) 2).getStonesCount();
        byte player1StonesPit5 = board.getPitByPlayerAndPosition(player1, (byte) 5).getStonesCount();
        byte player1StonesLargePit6 = board.getPitByPlayerAndPosition(player1, (byte) 6).getStonesCount();

        assertEquals(0, player2StonesPit4);
        assertEquals(0, player2StonesPit5);
        assertEquals(20, player2StonesLargePit6);

        assertEquals(0, player1StonesPit2);
        assertEquals(0, player1StonesPit5);
        assertEquals(52, player1StonesLargePit6);
        assertEquals(player1, board.getTurnPlayerId());
        assertEquals(Match.Status.COMPLETED, board.getStatus());
        assertEquals(player1, board.getWinnerId());
    }
}
