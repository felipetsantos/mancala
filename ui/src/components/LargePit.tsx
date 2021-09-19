import React, { FC } from "react";

interface LargePitProps {
  stonesCount: number;
  right: boolean;
}

const LargePit: FC<LargePitProps> = ({ stonesCount, right }) => {
  return (
    <>
      <div className={"large-pit " + (right ? "large-pit-p1" : "large-pit-p2")}>
        <span className="counter">{stonesCount}</span>
      </div>
    </>
  );
};

export default LargePit;
