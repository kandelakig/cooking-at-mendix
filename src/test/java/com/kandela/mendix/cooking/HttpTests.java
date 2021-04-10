package com.kandela.mendix.cooking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class HttpTests {

  @Autowired
  private WebTestClient webClient;

  @Test
  void validEndpointsShouldReturnStatus200() {
    webClient.get().uri("/categories").exchange().expectStatus().isOk();
    webClient.get().uri("/recipes").exchange().expectStatus().isOk();
  }

  @Test
  void invalidEndpointsShouldReturnStatus404() {
    webClient.get().uri("/").exchange().expectStatus().isNotFound();
    webClient.get().uri("/viruses").exchange().expectStatus().isNotFound();
  }

}
