package com.bol.mancala.controller;

import com.bol.mancala.dto.Move;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.Match;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("When a board is requested then it is returned")
    void boardRequestCorrectly() throws Exception {
        Board board = mapper
                .readValue(
                        mockMvc
                                .perform(
                                        get("/api/board/1")
                                )
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Board.class);
        assertEquals(Match.Status.IN_PROGRESS, board.getStatus());
    }

    @Test
    @DisplayName("When a player executes sows move then the endpoint will return a board in a new state that reflects the move executed")
    void boardSowsCorrectly() throws Exception {
        Move move = new Move(1, (byte) 0);
        Board board = mapper
                .readValue(
                        mockMvc
                                .perform(
                                        put("/api/board/1/sows")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(move)))
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Board.class);
        assertEquals(1, board.getPlayerPits(1).get(6).getStonesCount());
    }
}
