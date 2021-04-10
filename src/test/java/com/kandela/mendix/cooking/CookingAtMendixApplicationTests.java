package com.kandela.mendix.cooking;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kandela.mendix.cooking.controller.CategoryController;
import com.kandela.mendix.cooking.controller.RecipeController;
import com.kandela.mendix.cooking.repo.CategoryRepository;
import com.kandela.mendix.cooking.repo.RecipeRepository;

@SpringBootTest
class CookingAtMendixApplicationTests {

  @Autowired
  private RecipeRepository recipeRepo;
  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private RecipeController recipeController;
  @Autowired
  private CategoryController categoryController;

  @Test
  void contextLoads() {
    assertNotNull(recipeRepo);
    assertNotNull(categoryRepo);
    assertNotNull(recipeController);
    assertNotNull(categoryController);
  }

}
