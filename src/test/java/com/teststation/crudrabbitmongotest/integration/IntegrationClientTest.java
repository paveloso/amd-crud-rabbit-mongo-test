package com.teststation.crudrabbitmongotest.integration;

import com.teststation.crudrabbitmongotest.CrudRabbitMongoTestApplication;
import io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

//import org.junit.jupiter.api.Assertions;

@SpringBootTest(classes = CrudRabbitMongoTestApplication.class)
@RunWith(SpringRunner.class)
public class IntegrationClientTest {

    @Autowired
    private IntegrationClient integrationClient;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(
            service(HoverflyMatchers.matches("webhook.site"))
                    .post("/ee16bb3d-0009-484d-aed6-9df445ef97e6")
                    .body("\"{ \"player\": \"test\" }")
                    .willReturn(success("oke oke", "text/plain"))
    ));

    @Test
    public void makeCallToWebHook() {
        String json = "\"{ \"player\": \"test\" }";
        int responseCode = integrationClient.sendData(json);
        Assertions.assertThat(responseCode).isEqualTo(HttpStatus.OK.value());
    }
}
