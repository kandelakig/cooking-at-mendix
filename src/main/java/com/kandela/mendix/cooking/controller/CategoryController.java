package com.kandela.mendix.cooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kandela.mendix.cooking.model.Category;
import com.kandela.mendix.cooking.repo.CategoryRepository;

@RestController
public class CategoryController {

  @Autowired
  private final CategoryRepository repo;

  public CategoryController(CategoryRepository repo) {
    super();
    this.repo = repo;
  }

  @RequestMapping(method = RequestMethod.GET, path = "/categories", produces = "application/json")
  public List<Category> list() {
    return repo.findAll();
  }

}
