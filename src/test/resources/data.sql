SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE match_player;
TRUNCATE TABLE pit;
TRUNCATE TABLE player;
TRUNCATE TABLE match;

SET REFERENTIAL_INTEGRITY TRUE;
ALTER TABLE pit ALTER COLUMN id RESTART WITH 1;
ALTER TABLE match ALTER COLUMN id RESTART WITH 1;
ALTER TABLE player ALTER COLUMN id RESTART WITH 1;

INSERT INTO player (id, name, username, created_at) VALUES (1, 'Player 1', 'player1', '2021-09-16 00:00:00');
INSERT INTO player (id, name, username, created_at) VALUES (2, 'Player 2', 'player2', '2021-09-16 00:00:00');


INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (1, '2021-09-16 00:00:00', null, 3, 1, null, 0);

INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (2, '2021-09-16 00:00:00', null, 3, 1, null, 0);

INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (3, '2021-09-16 00:00:00', null, 3, 1, null, 0);

INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (4, '2021-09-16 00:00:00', null, 3, 2, null, 0);

INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (5, '2021-09-16 00:00:00', null, 2, 1, null, 0);

INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (6, '2021-09-16 00:00:00', null, 3, 2, null, 0);

INSERT INTO match (id, started_at, ended_at, status, turn_player_id, winner_player_id, draw)
VALUES (7, '2021-09-16 00:00:00', null, 3, 1, null, 0);

INSERT INTO match_player (match_id, player_id) VALUES (1, 1);
INSERT INTO match_player (match_id, player_id) VALUES (1, 2);


INSERT INTO match_player (match_id, player_id) VALUES (2, 1);
INSERT INTO match_player (match_id, player_id) VALUES (2, 2);

INSERT INTO match_player (match_id, player_id) VALUES (3, 1);
INSERT INTO match_player (match_id, player_id) VALUES (3, 2);

INSERT INTO match_player (match_id, player_id) VALUES (4, 1);
INSERT INTO match_player (match_id, player_id) VALUES (4, 2);

INSERT INTO match_player (match_id, player_id) VALUES (5, 1);

INSERT INTO match_player (match_id, player_id) VALUES (6, 1);
INSERT INTO match_player (match_id, player_id) VALUES (6, 2);

INSERT INTO match_player (match_id, player_id) VALUES (7, 1);
INSERT INTO match_player (match_id, player_id) VALUES (7, 2);


-- Initial board setup
------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
-- player1     -------     6       6       6       6       6       6      -----
--  LARGE PIT |  0   |---------------------------------------------------|  0  | LARGE PIT
-- player2     ______      6      6       6       6       6       6       _____
------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (1, 1, 1, 1, 6, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (2, 1, 1, 1, 6, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (3, 1, 1, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (4, 1, 1, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (5, 1, 1, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (6, 1, 1, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (7, 1, 1, 2, 0, 6);

INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (8, 1, 2, 1, 6, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (9, 1, 2, 1, 6, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (10, 1, 2, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (11, 1, 2, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (12, 1, 2, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (13, 1, 2, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (14, 1, 2, 2, 0, 6);



 -- Test sows stones of a pit that will land the last stone in the opponent's pits
 -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
 -- player1      ------      7       7       7       7       7       0     -------
 --  LARGE PIT  |  1  |---------------------------------------------------|  0  | LARGE PIT
 -- player2     ------       6      6       6       6       6       6     ------
 ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (15, 2, 1, 1, 0, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (16, 2, 1, 1, 7, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (17, 2, 1, 1, 7, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (18, 2, 1, 1, 7, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (19, 2, 1, 1, 7, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (20, 2, 1, 1, 7, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (21, 2, 1, 2, 1, 6);

INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (22, 2, 2, 1, 6, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (23, 2, 2, 1, 6, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (24, 2, 2, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (25, 2, 2, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (26, 2, 2, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (27, 2, 2, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (28, 2, 2, 2, 0, 6);



 -- initial board
 -------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
 -- player1     -------      8       8       8       8       0       0    ------
 --  LARGE PIT |  2   |--------------------------------------------------|  0  | LARGE PIT
 -- player2    -------       7      7       6       6       6       6    ------
 ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (29, 3, 1, 1, 0, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (30, 3, 1, 1, 0, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (31, 3, 1, 1, 8, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (32, 3, 1, 1, 8, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (33, 3, 1, 1, 8, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (34, 3, 1, 1, 8, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (35, 3, 1, 2, 2, 6);

INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (36, 3, 2, 1, 7, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (37, 3, 2, 1, 7, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (38, 3, 2, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (39, 3, 2, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (40, 3, 2, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (41, 3, 2, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (42, 3, 2, 2, 0, 6);


 --- Initial board setup
 ----------------------- |Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
 --- player1    --------    0       8       8       8       0       0       -------
 ---  LARGE PIT |  11   |---------------------------------------------------|  0  | LARGE PIT
 --- player2    --------    8      8       7       7       7        0       -------
 ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (43, 4, 1, 1, 0, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (44, 4, 1, 1, 0, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (45, 4, 1, 1, 8, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (46, 4, 1, 1, 8, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (47, 4, 1, 1, 8, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (48, 4, 1, 1, 0, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (49, 4, 1, 2, 11, 6);

INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (50, 4, 2, 1, 8, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (51, 4, 2, 1, 8, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (52, 4, 2, 1, 7, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (53, 4, 2, 1, 7, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (54, 4, 2, 1, 7, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (55, 4, 2, 1, 0, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (56, 4, 2, 2, 0, 6);

-- Match 5 only player 1 pits
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (57, 5, 1, 1, 6, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (58, 5, 1, 1, 6, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (59, 5, 1, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (60, 5, 1, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (61, 5, 1, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (62, 5, 1, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (63, 5, 1, 2, 0, 6);


 --- Initial board setup
 ----------------------- |Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
 --- player1    --------    10      0       0      13       0       0       -------
 ---  LARGE PIT |  29   |---------------------------------------------------|  19  | LARGE PIT
 --- player2    --------    0      0        0      0       1        0       -------
 ------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (64, 6, 1, 1, 0, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (65, 6, 1, 1, 0, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (66, 6, 1, 1, 13, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (67, 6, 1, 1, 0, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (68, 6, 1, 1, 0, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (69, 6, 1, 1, 10, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (70, 6, 1, 2, 29, 6);

INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (71, 6, 2, 1, 0, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (72, 6, 2, 1, 0, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (73, 6, 2, 1, 0, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (74, 6, 2, 1, 0, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (75, 6, 2, 1, 1, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (76, 6, 2, 1, 0, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (77, 6, 2, 2, 19, 6);


-- Initial board setup
------------------------|Pit 5| |Pit 4| |Pit 3| |Pit 2| |Pit 1| |Pit 0|
-- player1     -------     6       6       6       6       6       6      -----
--  LARGE PIT |  0   |---------------------------------------------------|  0  | LARGE PIT
-- player2     ______      6      6       6       6       6       6       _____
------------------------|Pit 0| |Pit 1| |Pit 2| |Pit 3| |Pit 4| |Pit 5|
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (78, 7, 1, 1, 6, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (79, 7, 1, 1, 6, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (80, 7, 1, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (81, 7, 1, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (82, 7, 1, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (83, 7, 1, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (84, 7, 1, 2, 0, 6);

INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (85, 7, 2, 1, 6, 0);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (86, 7, 2, 1, 6, 1);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (87, 7, 2, 1, 6, 2);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (88, 7, 2, 1, 6, 3);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (89, 7, 2, 1, 6, 4);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (90, 7, 2, 1, 6, 5);
INSERT INTO pit (id, match_id, player_id, type, stones_count, position) VALUES (91, 7, 2, 2, 0, 6);