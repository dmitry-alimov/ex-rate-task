package com.gd.alimov.exrateproducer;

import com.gd.alimov.mvc.controller.ServerExchangeRatesController;
import com.gd.alimov.service.ExchangeRatesServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseClassTest {

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new ServerExchangeRatesController
                (new ExchangeRatesServiceImpl()));
    }
}