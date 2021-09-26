package com.ftsantos.mancala.controller;
import com.ftsantos.mancala.model.Player;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("When a Player Creation is requested then it is persisted")
    void playerCreatedCorrectly() throws Exception {
        LocalDateTime createdAt = LocalDateTime.of(2021, 9, 13, 10, 21, 0, 0);
        Player player = Player.builder()
                .setName("Player 1")
                .setUsername("restCreatePlayer1")
                .setCreatedAt(createdAt)
                .build();
        Player playerCreated = mapper
                .readValue(
                        mockMvc
                                .perform(
                                        post("/api/player")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(player)))
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Player.class);
        assertEquals(createdAt, playerCreated.getCreatedAt());
        assertTrue(playerCreated.getId() > 0);
    }

    @Test
    @DisplayName("When a Player is requested then it is returned")
    void playerRequestCorrectly() throws Exception {
        Player player = mapper
                .readValue(
                        mockMvc
                                .perform(
                                        get("/api/player/1")
                                )
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Player.class);
        assertEquals(1, player.getId());
    }
}
