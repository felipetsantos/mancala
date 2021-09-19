import React, { FC, useEffect, useState } from "react";
import { getBoard } from "../actions/StartMatch";
import { Start } from "../components/Start";
import { Winner } from "../components/Winner";
import { schemas } from "../types/api/schemas";
import { StorageService } from "../util/StorageService";
import Board from "./Board";
import { Loading } from "../components/Loading";
const Mancala: FC = () => {
  return (
    <>
      {" "}
      <h1 className="title">Mancala</h1>
      <div className="game-holder">
        <GameHolder />
      </div>
    </>
  );
};

const GameHolder: FC = () => {
  const [board, setBoard] = useState<schemas.Board>();
  const [isLoading, setLoading] = useState(false);
  useEffect(() => {
    if (StorageService.getInstance().contains("session")) {
      const session: schemas.LocalSession =
        StorageService.getInstance<schemas.LocalSession>().getData("session");
      getBoard(session.matchId, setLoading, setBoard);
    }
  }, []);
  if (board && (!board.players || !board.pits)) {
    const session: schemas.LocalSession =
      StorageService.getInstance<schemas.LocalSession>().getData("session");
    getBoard(session.matchId, setLoading, setBoard);
  }

  if (isLoading) {
    return <Loading />;
  }

  if (!board) {
    return <Start setBoard={setBoard} />;
  }
  return (
    <>
      {board?.status === schemas.MatchStatus.COMPLETED.toString() && (
        <Winner board={board} setBoard={setBoard} />
      )}
      <Board board={board} setBoard={setBoard} />
    </>
  );
};

export default Mancala;
