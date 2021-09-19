import { schemas } from "../types/api/schemas";

export const setStateBoard = (
  board: schemas.Board,
  setBoard: (board: any) => void
) => {
  setBoard({
    matchId: board.matchId,
    status: board.status,
    pits: board.pits?.map((pit) => {
      return {
        id: pit.id,
        stonesCount: pit.stonesCount,
        position: pit.position,
        type: pit.type,
        player: pit.player,
      };
    }),
    players: board.players,
    turnPlayerId: board.turnPlayerId,
    winnerId: board.winnerId,
    draw: board.draw,
  });
};
