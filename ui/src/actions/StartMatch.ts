import { requests } from "../types/api/requests";
import { schemas } from "../types/api/schemas";
import { StorageService } from "../util/StorageService";
import { setStateBoard } from "../util/SetStateBoard";
import {
  defaultErrorHandler,
  handleErrors,
  isError,
} from "../util/HandleRequestErrors";

export const startMatch = (
  setLoading: (loading: boolean) => void,
  setBoard: (board: any) => void
) => {
  setLoading(true);
  const player1Id = process.env.REACT_APP_PLAYER_1_ID;
  requests.postMatchStart(
    { pathParams: { playerId: player1Id ? parseInt(player1Id) : 0 } },
    (result) => {
      if (isError(result)) {
        handleErrors(result, setLoading);
        return;
      }
      jointToMatch(result.id, setLoading, setBoard);
    },
    (error) => {
      defaultErrorHandler(error, setLoading);
    }
  );
};

const jointToMatch = (
  matchId: number,
  setLoading: (loading: boolean) => void,
  setBoard: (board: any) => void
) => {
  const player2Id = process.env.REACT_APP_PLAYER_2_ID;
  requests.putMatchJoin(
    {
      pathParams: {
        playerId: player2Id ? parseInt(player2Id) : 0,
        matchId: matchId,
      },
    },
    (result) => {
      if (isError(result)) {
        handleErrors(result, setLoading);
        return;
      }
      getBoard(result.id, setLoading, setBoard);
    },
    (error) => {
      defaultErrorHandler(error, setLoading);
    }
  );
};

export const getBoard = (
  matchId: number,
  setLoading: (loading: boolean) => void,
  setBoard: (board: any) => void
) => {
  requests.getBoard(
    { pathParams: { matchId: matchId } },
    (result) => {
      if (isError(result)) {
        handleErrors(result, setLoading);
        return;
      }
      const board: schemas.Board = { ...result };
      if (!board.pits || !board.players) {
        return;
      }
      setStateBoard(board, setBoard);
      const session: schemas.LocalSession = {
        matchId: board.matchId,
        status: board.status,
      };
      StorageService.getInstance().saveData("session", session);
      setLoading(false);
    },
    (error) => {
      defaultErrorHandler(error, setLoading);
    }
  );
};
