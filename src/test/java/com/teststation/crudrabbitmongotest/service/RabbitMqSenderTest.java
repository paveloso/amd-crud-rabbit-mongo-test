package com.teststation.crudrabbitmongotest.service;

import com.teststation.crudrabbitmongotest.CrudRabbitMongoTestApplication;
import com.teststation.crudrabbitmongotest.model.Player;
import com.teststation.crudrabbitmongotest.rest.controller.PlayerController;
import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = CrudRabbitMongoTestApplication.class)
@ComponentScan(value = {"com.teststation.crudrabbitmongotest.service"})
@RunWith(SpringRunner.class)
public class RabbitMqSenderTest {

    @Autowired
    private PlayerController playerController;
    @MockBean
    private RabbitMqSender mqSender;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue testQueue;
    @Value("${rabbit.exchange}")
    private String testExchange;
    @Value("${rabbit.routingkey}")
    private String testRoutingkey;
    @Autowired
    private MapperFacade mapperFacade;
    @MockBean
    private PlayerService playerService;

    private ObjectMapper jsonMapper = new ObjectMapper();

    private PlayerDto playerDto;

    @Before
    public void init() {
        playerDto = new PlayerDto("player", "country", 1);
        Mockito.when(playerService.savePlayer(playerDto)).thenReturn(mapperFacade.map(playerDto, Player.class));
        playerController.savePlayer(playerDto);
    }

    @Test
    public void sendMessageInvokedAfterPlayerSaved() {
        Mockito.verify(mqSender, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    public void messageDeliveredToQueueAfterPlayerSaved() throws JsonProcessingException {
        rabbitTemplate.convertAndSend(testExchange, testRoutingkey, jsonMapper.writeValueAsString(mapperFacade.map(playerDto, Player.class)));
        Message receivedMessage = rabbitTemplate.receive(testQueue.getName());
        Assert.assertNotNull(receivedMessage);
        Assert.assertNotNull(receivedMessage.getBody());
    }
}
