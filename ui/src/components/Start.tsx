import React, { FC, useState } from "react";
import { startMatch } from "../actions/StartMatch";

interface MancalaStartProps {
  setBoard: (board: any) => void;
}

export const Start: FC<MancalaStartProps> = ({ setBoard }) => {
  const [isLoading, setLoading] = useState(false);

  return (
    <div className="finished show">
      <span className="hover"></span>
      <p className="button button-new-match">
        {isLoading ? (
          "Loading..."
        ) : (
          <span
            className="btn"
            onClick={() => {
              startMatch(setLoading, setBoard);
            }}
          >
            Start new match
          </span>
        )}
      </p>
    </div>
  );
};
