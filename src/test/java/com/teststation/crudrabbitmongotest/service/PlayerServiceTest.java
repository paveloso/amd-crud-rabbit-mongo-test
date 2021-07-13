package com.teststation.crudrabbitmongotest.service;

import com.lordofthejars.nosqlunit.annotation.CustomComparisonStrategy;
import com.lordofthejars.nosqlunit.annotation.IgnorePropertyValue;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.MongoFlexibleComparisonStrategy;
import com.teststation.crudrabbitmongotest.CrudRabbitMongoTestApplication;
import com.teststation.crudrabbitmongotest.dao.PlayerRepository;
import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = CrudRabbitMongoTestApplication.class)
@CustomComparisonStrategy(comparisonStrategy = MongoFlexibleComparisonStrategy.class)
@ComponentScan(value = {"com.teststation.crudrabbitmongotest.service"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PlayerServiceTest {

    private static final String DB_NAME_TEST = "test";

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @ClassRule
//    public static ManagedMongoDb mongoDb = newManagedMongoDbRule()
//            .mongodPath("C:\\Program Files\\MongoDB\\Server\\4.4")
//            .appendSingleCommandLineArguments("-vvv")
//            .build();

    @Rule
    public MongoDbRule mongoDbRule = new MongoDbRule(MongoDbConfigurationBuilder.mongoDb().databaseName(DB_NAME_TEST).host("192.168.20.118").build());

    @UsingDataSet(locations = "initial-dataset-creation.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    @ShouldMatchDataSet(location = "save-single-player-test.json")
    @IgnorePropertyValue(properties = {"creationDate"})
    @Test
    public void savePlayerCompleted() {
        PlayerDto player = new PlayerDto("player15", "country", 1);
        playerService.savePlayer(player);
    }

    @UsingDataSet(locations = "initial-dataset-deletion.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    @ShouldMatchDataSet(location = "delete-players-test.json")
    @IgnorePropertyValue(properties = {"creationDate"})
    @Test
    public void deletePlayersCompleted() {
        playerService.deletePlayer("playerd1");
        playerService.deletePlayer("playerd2");
    }
}
