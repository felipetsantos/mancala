import { requests } from "../types/api/requests";
import { schemas } from "../types/api/schemas";
import { handleErrors, isError } from "../util/HandleRequestErrors";
import { StorageService } from "../util/StorageService";

export const endMatch = (
  matchId: number,
  playerId: number,
  setLoading: (loading: boolean) => void,
  setBoard: (board: any) => void
) => {
  setLoading(true);
  requests.putEndMatch(
    {
      pathParams: { matchId: matchId, playerId: playerId },
    },
    (result) => {
      if (isError(result)) {
        handleErrors(result, setLoading);
        return;
      }
      const match: schemas.Match = { ...result };
      if (
        match &&
        match.status &&
        match.status === schemas.MatchStatus.ABANDONED
      ) {
        StorageService.getInstance().removeItem("session");
        setBoard(undefined);
      }
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
