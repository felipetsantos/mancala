import React, { FC, useState } from "react";
import { schemas } from "../types/api/schemas";
import { PlayerPits } from "../components/PlayerPits";
import LargePit from "../components/LargePit";
import { Loading } from "../components/Loading";
import { endMatch } from "../actions/EndMatch";

interface BoardProps {
  board: schemas.Board;
  setBoard: (board: any) => void;
}
const Board: FC<BoardProps> = ({ board, setBoard }) => {
  const [player1, player2] = board.players;
  const [isLoading, setLoading] = useState(false);
  if (!player1 || !player2) {
    return <>Invalid players</>;
  }
  const clickOptions = {
    playerId: board.turnPlayerId,
    setBoard: setBoard,
    matchId: board.matchId,
    setLoading,
  };
  let clickOptionsPlayer1: null | any = clickOptions;
  let clickOptionsPlayer2 = null;
  if (board.turnPlayerId === player2.id) {
    clickOptionsPlayer2 = clickOptions;
    clickOptionsPlayer1 = null;
  }

  return (
    <>
      {isLoading && <Loading />}
      <div className="player player-p2">
        <span>
          {player2.name}: {getPlayingLabel(player2.id, board.turnPlayerId)}
        </span>
      </div>
      <div className="board">
        <div className="column">
          <LargePit
            stonesCount={getPlayerLargePit(player2.id, board.pits).stonesCount}
            right={false}
          />
        </div>
        <div className="column pits-holder">
          <div className="row row-p2">
            <PlayerPits
              pits={getPlayerPits(player2.id, sortPitsDesc(board.pits))}
              clickOptions={clickOptionsPlayer2}
            />
          </div>
          <div className="row row-p1">
            <PlayerPits
              pits={getPlayerPits(player1.id, board.pits)}
              clickOptions={clickOptionsPlayer1}
            />
          </div>
        </div>
        <div className="column">
          <LargePit
            stonesCount={getPlayerLargePit(player1.id, board.pits).stonesCount}
            right={true}
          />
        </div>
      </div>
      <div className="player player-p1">
        <span>
          {player1.name}: {getPlayingLabel(player1.id, board.turnPlayerId)}
        </span>
      </div>
      <div>
        <span
          className="btn link"
          onClick={() =>
            endMatch(board.matchId, board.turnPlayerId, setLoading, setBoard)
          }
        >
          End Match
        </span>
      </div>
    </>
  );
};

const sortPitsDesc = (pits: schemas.Pit[]) => {
  return [...pits].sort((a, b) => {
    if (a.position > b.position) {
      return -1;
    } else if (a.position < b.position) {
      return 1;
    } else {
      return 0;
    }
  });
};
const getPlayingLabel = (playerId: number, currentPlayerId: number) => {
  return playerId === currentPlayerId ? "Play Now" : "Wait";
};

const getPlayerPits = (playerId: number, pits: schemas.Pit[]) => {
  return pits
    .filter((p) => p.player.id === playerId)
    .filter((p) => p.type === schemas.PitType.NORMAL.toString());
};

const getPlayerLargePit = (playerId: number, pits: schemas.Pit[]) => {
  return pits
    .filter((p) => p.player.id === playerId)
    .filter((p) => p.type === schemas.PitType.LARGE.toString())[0];
};
export default Board;
