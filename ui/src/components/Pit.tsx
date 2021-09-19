import React, { FC } from "react";
import { sows } from "../actions/Sows";

interface PitProps {
  stonesCount: number;
  position: number;
  clickOptions?: null | {
    playerId: number;
    matchId: number;
    setLoading: (loading: boolean) => void;
    setBoard: (board: any) => void;
  };
}

const Pit: FC<PitProps> = ({ clickOptions, stonesCount, position }) => {
  //const isClickable = stonesCount !== 0 && clickOptions;
  const isClickable = clickOptions;
  return (
    <>
      <div
        className={`pit ${isClickable ? "clickable" : ""}`}
        onClick={() => {
          if (!clickOptions || !isClickable) {
            return;
          }
          sows(
            clickOptions.matchId,
            clickOptions.playerId,
            position,
            clickOptions.setLoading,
            clickOptions.setBoard
          );
        }}
      >
        <span className="counter">{stonesCount}</span>
      </div>
    </>
  );
};

export default Pit;
