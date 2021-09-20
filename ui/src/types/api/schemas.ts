export namespace schemas {
  export interface BoardResponse {
    isLoading: boolean;
    error: any;
    data: Board;
  }
  export interface Match {
    id: number;
    status: MatchStatus;
  }
  export interface Player {
    id: number;
    name: string;
    username: string;
  }

  export interface Pit {
    id: number;
    stonesCount: number;
    position: number;
    type: string;
    player: Player;
  }

  export enum PitType {
    NORMAL = "NORMAL",
    LARGE = "LARGE",
  }

  export interface Board {
    matchId: number;
    pits: Pit[];
    players: Player[];
    turnPlayerId: number;
    winnerId: number;
    status: string;
    draw: boolean;
  }

  export interface LocalSession {
    matchId: number;
    status: string;
  }
  export enum MatchStatus {
    WAITING_PLAYERS = "WAITING_PLAYERS",
    WAITING_SECOND_PLAYER = "WAITING_SECOND_PLAYER",
    IN_PROGRESS = "IN_PROGRESS",
    ABANDONED = "ABANDONED",
    COMPLETED = "COMPLETED",
  }
}
