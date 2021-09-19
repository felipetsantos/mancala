DROP TABLE IF EXISTS player;

CREATE TABLE player (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  username VARCHAR(250) NOT NULL,
  created_at DATETIME DEFAULT NULL
);

ALTER TABLE player
ADD CONSTRAINT player_username_UNIQUE
UNIQUE (username);

DROP TABLE IF EXISTS match;

CREATE TABLE match (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  started_at DATETIME NOT NULL,
  ended_at DATETIME DEFAULT NULL,
  status TINYINT NOT NULL,
  turn_player_id BIGINT,
  winner_player_id BIGINT,
  draw BIT
);

ALTER TABLE match
    ADD FOREIGN KEY (turn_player_id)
    REFERENCES player(id);

ALTER TABLE match
    ADD FOREIGN KEY (winner_player_id)
    REFERENCES player(id);

DROP TABLE IF EXISTS match_player;

CREATE TABLE match_player (
    match_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    CONSTRAINT match_player_pk PRIMARY KEY (
        match_id,player_id
    )
);

ALTER TABLE match_player
    ADD FOREIGN KEY (match_id)
    REFERENCES match(id);

ALTER TABLE match_player
    ADD FOREIGN KEY (player_id)
    REFERENCES player(id);

DROP TABLE IF EXISTS pit;

CREATE TABLE pit (
    id BIGINT AUTO_INCREMENT  PRIMARY KEY,
    match_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    type TINYINT NOT NULL,
    stones_count TINYINT DEFAULT 0,
    position TINYINT NOT NULL
);


ALTER TABLE pit
    ADD FOREIGN KEY (match_id)
    REFERENCES match(id);

ALTER TABLE pit
    ADD FOREIGN KEY (player_id)
    REFERENCES player(id);

ALTER TABLE pit
ADD CONSTRAINT pit_match_id_player_id_position_UNIQUE
UNIQUE (match_id, player_id, position);