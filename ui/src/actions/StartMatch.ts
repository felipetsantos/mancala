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
  requests.postMatchStart(
    { pathParams: { playerId: 1 } },
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
  requests.putMatchJoin(
    { pathParams: { playerId: 2, matchId: matchId } },
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
