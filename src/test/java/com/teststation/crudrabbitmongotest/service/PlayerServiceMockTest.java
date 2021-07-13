package com.teststation.crudrabbitmongotest.service;

import com.teststation.crudrabbitmongotest.CrudRabbitMongoTestApplication;
import com.teststation.crudrabbitmongotest.dao.PlayerRepository;
import com.teststation.crudrabbitmongotest.model.Player;
import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

//import org.junit.jupiter.api.Assertions;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrudRabbitMongoTestApplication.class)
public class PlayerServiceMockTest {

    @MockBean
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ApplicationContext context;

    @Test
    public void methodsCalledInServiceBean() {
        Player player = playerService.savePlayer(new PlayerDto("Player", "country", 1));
        Mockito.verify(playerRepository, Mockito.times(1)).findByName("Player");
    }

    @Test
    public void playerRepoReturnPlayerWhenSuccess() {
        Player expectedPlayer = new Player("Player", "country", 1);
        Mockito.when(playerRepository.findByName("Player"))
                .thenReturn(expectedPlayer);

        PlayerRepository playerRepoFromContext = context.getBean(PlayerRepository.class);

        Player actualPlayer = playerRepoFromContext.findByName("Player");
        Assertions.assertThat(actualPlayer).isEqualTo(expectedPlayer);
        Mockito.verify(playerRepository).findByName("Player");
    }

    @Test
    public void playerRepoReturnNullWhenNotFound() {
        Mockito.when(playerRepository.findByName("Unknown"))
                .thenReturn(null);

        Player actualPlayer = playerRepository.findByName("Unknown");
        Assertions.assertThat(actualPlayer).isNull();
    }
}
