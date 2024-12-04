package com.froxylabs.aidemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AiDemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldReceiveResponseWithOKStatusCode() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/ai/generate?message=I%20love%20coffee",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
