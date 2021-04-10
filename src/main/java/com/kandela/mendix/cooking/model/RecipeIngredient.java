package com.kandela.mendix.cooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

  @Id
  @GeneratedValue
  private Long id;

  private String item;

  private String quantity;

  private String unit;

  public RecipeIngredient() {
    super();
  }

  public RecipeIngredient(String item, String quantity, String unit) {
    super();
    this.item = item;
    this.quantity = quantity;
    this.unit = unit;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

}
