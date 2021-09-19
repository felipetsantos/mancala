export namespace requests {
  const removeEndUrlSlash = (url?: string) => {
    if (!url) return "";
    return url.replace(/\/$/, "");
  };

  const API_URL = removeEndUrlSlash(process.env.REACT_APP_SERVER_URL);
  enum HttpMethods {
    GET = "GET",
    PUT = "PUT",
    POST = "POST",
    DELETE = "DELETE",
  }
  /**
   * Board Get
   */
  export interface GetBoardParams {
    pathParams: {
      matchId: number;
    };
  }

  export const getBoard = (
    params: GetBoardParams,
    onSuccess: (result: any) => void,
    onError: (error: any) => void
  ) => {
    fetch(`${API_URL}/board/${params.pathParams.matchId}`)
      .then((res) => res.json())
      .then(onSuccess, onError);
  };

  /**
   * Match start
   */
  export interface PostMatchStartParams {
    pathParams: {
      playerId: number;
    };
  }

  export const postMatchStart = (
    params: PostMatchStartParams,
    onSuccess: (result: any) => void,
    onError: (error: any) => void
  ) => {
    fetch(`${API_URL}/match/start/${params.pathParams.playerId}`, {
      method: HttpMethods.POST,
    })
      .then((res) => res.json())
      .then(onSuccess, onError);
  };

  /**
   * Match Join
   */
  export interface PutMatchJoinParams {
    pathParams: {
      matchId: number;
      playerId: number;
    };
  }

  export const putMatchJoin = (
    params: PutMatchJoinParams,
    onSuccess: (result: any) => void,
    onError: (error: any) => void
  ) => {
    fetch(
      `${API_URL}/match/${params.pathParams.matchId}/join/${params.pathParams.playerId}`,
      {
        method: HttpMethods.PUT,
      }
    )
      .then((res) => res.json())
      .then(onSuccess, onError);
  };

  /**
   * Board Sows
   */
  export interface PutBoardSowsParams {
    pathParams: {
      matchId: number;
    };
    body: {
      playerId: number;
      positionPitSelected: number;
    };
  }

  export const putBoardSows = (
    params: PutBoardSowsParams,
    onSuccess: (result: any) => void,
    onError: (error: any) => void
  ) => {
    fetch(`${API_URL}/board/${params.pathParams.matchId}/sows`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: HttpMethods.PUT,
      body: JSON.stringify(params.body),
    })
      .then((res) => res.json())
      .then(onSuccess, onError);
  };
}
