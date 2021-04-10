package com.kandela.mendix.cooking.xml;

public class Recipe {
  private Head head;
  private Ingredients ingredients;
  private Directions directions;

  public Head getHead() {
    return head;
  }

  public void setHead(Head head) {
    this.head = head;
  }

  public Ingredients getIngredients() {
    return ingredients;
  }

  public void setIngredients(Ingredients ingredients) {
    this.ingredients = ingredients;
  }

  public Directions getDirections() {
    return directions;
  }

  public void setDirections(Directions directions) {
    this.directions = directions;
  }

}
