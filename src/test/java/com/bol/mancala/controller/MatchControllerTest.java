package com.bol.mancala.controller;

import com.bol.mancala.model.Match;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("When a player want start match then it is started")
    void matchStartedCorrectly() throws Exception {
        Match match = mapper
                .readValue(
                        mockMvc
                                .perform(
                                        post("/api/match/start/1")
                                )
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Match.class);
        assertEquals(Match.Status.WAITING_SECOND_PLAYER, match.getStatus());
        assertTrue(match.getId() > 0);
    }

    @Test
    @DisplayName("When a player wants join to a match in the status WAITING_SECOND_PLAYER then he joins to it")
    void matchPlayerJoinCorrectly() throws Exception {
        Match match = mapper
                .readValue(
                        mockMvc
                                .perform(
                                        put("/api/match/5/join/2")
                                )
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Match.class);
        assertEquals(Match.Status.IN_PROGRESS, match.getStatus());
        assertTrue(match.getId() > 0);
        assertTrue(match.getPlayers().stream().filter(p -> p.getId() == 2).findFirst().isPresent());
    }
}
