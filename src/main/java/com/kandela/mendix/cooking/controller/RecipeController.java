package com.kandela.mendix.cooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kandela.mendix.cooking.model.Recipe;
import com.kandela.mendix.cooking.repo.RecipeRepository;

@RestController
public class RecipeController {

  @Autowired
  private final RecipeRepository repo;

  public RecipeController(RecipeRepository repo) {
    super();
    this.repo = repo;
  }

  @RequestMapping(method = RequestMethod.GET, path = "/recipes", produces = "application/json")
  public List<Recipe> list() {
    return repo.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, path = "/recipes", consumes = "application/json", produces = "application/json")
  public Recipe add(@RequestBody Recipe newRecipe) {
    return repo.save(newRecipe);
  }
}
