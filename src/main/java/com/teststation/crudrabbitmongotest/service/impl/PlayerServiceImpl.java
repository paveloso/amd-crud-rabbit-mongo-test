package com.teststation.crudrabbitmongotest.service.impl;

import com.teststation.crudrabbitmongotest.dao.PlayerRepository;
import com.teststation.crudrabbitmongotest.exception.PlayerException;
import com.teststation.crudrabbitmongotest.model.Player;
import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;
import com.teststation.crudrabbitmongotest.service.PlayerService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public Player savePlayer(PlayerDto player) {
        Player dbPlayer = playerRepo.findByName(player.getName());

        if (dbPlayer != null) {
            throw new PlayerException("Player with provided name already exists");
        }
        Player playerToPersist = mapperFacade.map(player, Player.class);
        playerToPersist.setCreationDate(new Date());
        playerRepo.insert(playerToPersist);

        return playerToPersist;
    }

    @Override
    public List<PlayerDto> getPlayers() {
        return playerRepo.findAll().stream().map(player -> mapperFacade.map(player, PlayerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePlayer(String name) {
        Optional<Player> player = Optional.of(playerRepo.findByName(name));
        player.ifPresent(pl -> playerRepo.delete(pl));
        return playerRepo.findByName(name) == null;
    }
}
