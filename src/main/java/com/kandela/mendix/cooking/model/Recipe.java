package com.kandela.mendix.cooking.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String title;

  @ManyToMany
  @JoinTable(name = "recipe_categories")
  private Set<Category> categories;

  private Integer yield;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private Set<RecipeIngredient> ingredients;

  @ElementCollection
  @Column(name = "step")
  private Set<String> steps;

  public Recipe() {
    super();
  }

  public Recipe(String title, Set<Category> categories, Integer yield, Set<RecipeIngredient> ingredients, Set<String> steps) {
    super();
    this.title = title;
    this.categories = categories;
    this.yield = yield;
    this.ingredients = ingredients;
    this.steps = steps;
  }

  public Recipe(Long id, String title, Set<Category> categories, Integer yield, Set<RecipeIngredient> ingredients, Set<String> steps) {
    super();
    this.id = id;
    this.title = title;
    this.categories = categories;
    this.yield = yield;
    this.ingredients = ingredients;
    this.steps = steps;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public Integer getYield() {
    return yield;
  }

  public void setYield(Integer yield) {
    this.yield = yield;
  }

  public Set<RecipeIngredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Set<RecipeIngredient> ingredients) {
    this.ingredients = ingredients;
  }

  public Set<String> getSteps() {
    return steps;
  }

  public void setSteps(Set<String> steps) {
    this.steps = steps;
  }

}
