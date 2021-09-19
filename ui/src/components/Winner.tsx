import { FC, useState } from "react";
import { startMatch } from "../actions/StartMatch";
import { schemas } from "../types/api/schemas";

interface WinnerProps {
  setBoard: (board: any) => void;
  board: schemas.Board;
}

export const Winner: FC<WinnerProps> = ({ board, setBoard }) => {
  const [isLoading, setLoading] = useState(false);
  return (
    <>
      <div className={`finished show`}>
        <span className="hover"></span>
        <p className="winner">
          {getWinnerName(board)}{" "}
          {isLoading ? (
            "Loading..."
          ) : (
            <span
              onClick={() => {
                startMatch(setLoading, setBoard);
              }}
              className="btn text-normal link"
            >
              <br /> Play again
            </span>
          )}
        </p>
      </div>
    </>
  );
};

const getWinnerName = (board: schemas.Board) => {
  if (board.draw) {
    return "Draw";
  }
  if (!board.winnerId) {
    return "No defined";
  }
  return (
    board.players.find((p) => {
      return p.id === board.winnerId;
    })?.name + " won!"
  );
};
