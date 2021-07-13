package com.teststation.crudrabbitmongotest.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RabbitMqSender {

    private static final Logger LOGGER = Logger.getLogger(RabbitMqSender.class.getName());

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Value("${rabbit.exchange}")
    private String exchange;
    @Value("${rabbit.routingkey}")
    private String routingkey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingkey, message);
        LOGGER.info("sent to mq, message = " + message);
    }
}
