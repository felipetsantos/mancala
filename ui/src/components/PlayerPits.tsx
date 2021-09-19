import React, { FC } from "react";
import { schemas } from "../types/api/schemas";
import Pit from "./Pit";

interface PlayerPitsProps {
  pits: schemas.Pit[];
  clickOptions?: null | {
    matchId: number;
    playerId: number;
    setBoard: (board: any) => void;
    setLoading: (loading: boolean) => void;
  };
}
export const PlayerPits: FC<PlayerPitsProps> = ({ pits, clickOptions }) => {
  return (
    <>
      {pits.map((pit) => {
        return (
          <Pit
            key={pit.id}
            stonesCount={pit.stonesCount}
            position={pit.position}
            clickOptions={clickOptions}
          />
        );
      })}
    </>
  );
};
