package com.kandela.mendix.cooking;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.kandela.mendix.cooking.controller.CategoryController;
import com.kandela.mendix.cooking.controller.RecipeController;
import com.kandela.mendix.cooking.repo.CategoryRepository;
import com.kandela.mendix.cooking.repo.RecipeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SanityTest {

  @Autowired
  private RecipeRepository recipeRepo;
  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private RecipeController recipeController;
  @Autowired
  private CategoryController categoryController;

  @Autowired
  private WebTestClient webClient;

  @Test
  void contextLoads() {
    assertNotNull(recipeRepo);
    assertNotNull(categoryRepo);
    assertNotNull(recipeController);
    assertNotNull(categoryController);
  }

  @Test
  void httpEndpointsShouldReturnStatus200() {
    webClient.get().uri("/recipes").exchange().expectStatus().isOk();
    webClient.get().uri("/categories").exchange().expectStatus().isOk();
  }

}
