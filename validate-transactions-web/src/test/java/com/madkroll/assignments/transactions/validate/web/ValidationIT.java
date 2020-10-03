package com.madkroll.assignments.transactions.validate.web;

import com.madkroll.assignments.transactions.validate.data.Record;
import com.madkroll.assignments.transactions.validate.data.Records;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationIT {

    @Autowired
    private ValidateRecordsController controller;

    @Test
    public void shouldSucceed() {
        WebTestClient
                .bindToController(controller)
                .build()
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/validate")
                                .build())
                .contentType(MediaType.APPLICATION_XML)
                .bodyValue(
                        new Records(
                                List.of(
                                        new Record(1, "account", "same-reference-1", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                                        new Record(1, "account", "same-reference-2", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
                                )
                        )
                )
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$.invalidRecords").exists()
                .jsonPath("$.invalidRecords").isNotEmpty()
                .jsonPath("$.invalidRecords[0].reference").isEqualTo(1);
    }

    @Test
    public void shouldReturn500OnInternalError() {
        WebTestClient
                .bindToController(controller)
                .build()
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/validate")
                                .build())
                .contentType(MediaType.APPLICATION_XML)
                .bodyValue("nonsense")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}