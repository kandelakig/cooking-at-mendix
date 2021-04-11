package com.kandela.mendix.cooking.model;

import java.util.List;

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
  private List<Category> categories;

  private Integer yield;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<RecipeIngredient> ingredients;

  @ElementCollection
  @Column(name = "step", length = 4096)
  private List<String> steps;

  private String timeNeeded;

  public Recipe() {
    super();
  }

  public Recipe(String title, List<Category> categories, Integer yield, List<RecipeIngredient> ingredients, List<String> steps, String timeNeeded) {
    super();
    this.title = title;
    this.categories = categories;
    this.yield = yield;
    this.ingredients = ingredients;
    this.steps = steps;
    this.timeNeeded = timeNeeded;
  }

  public Recipe(Long id, String title, List<Category> categories, Integer yield, List<RecipeIngredient> ingredients, List<String> steps, String timeNeeded) {
    this(title, categories, yield, ingredients, steps, timeNeeded);
    this.id = id;
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

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public Integer getYield() {
    return yield;
  }

  public void setYield(Integer yield) {
    this.yield = yield;
  }

  public List<RecipeIngredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<RecipeIngredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<String> getSteps() {
    return steps;
  }

  public void setSteps(List<String> steps) {
    this.steps = steps;
  }

  public String getTimeNeeded() {
    return timeNeeded;
  }

  public void setTimeNeeded(String timeNeeded) {
    this.timeNeeded = timeNeeded;
  }

}
