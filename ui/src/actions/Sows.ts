import { requests } from "../types/api/requests";
import { setStateBoard } from "../util/SetStateBoard";
import { handleErrors, isError } from "../util/HandleRequestErrors";

export const sows = (
  matchId: number,
  playerId: number,
  position: number,
  setLoading: (loading: boolean) => void,
  setBoard: (board: any) => void
) => {
  setLoading(true);
  requests.putBoardSows(
    {
      pathParams: { matchId: matchId },
      body: { playerId: playerId, positionPitSelected: position },
    },
    (result) => {
      if (isError(result)) {
        handleErrors(result, setLoading);
        return;
      }
      setStateBoard(result, setBoard);
      setLoading(false);
    },
    (error) => {
      if (isError(error)) {
        handleErrors(error, setLoading);
        return;
      }
      alert("Uknow error");
      setLoading(false);
      return;
    }
  );
};
