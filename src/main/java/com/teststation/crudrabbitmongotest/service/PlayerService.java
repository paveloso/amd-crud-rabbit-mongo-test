package com.teststation.crudrabbitmongotest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teststation.crudrabbitmongotest.model.Player;
import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    Player savePlayer(PlayerDto player);

    List<PlayerDto> getPlayers();

    boolean deletePlayer(String name);

    void processPlayer(PlayerDto playerDto) throws JsonProcessingException;

}
