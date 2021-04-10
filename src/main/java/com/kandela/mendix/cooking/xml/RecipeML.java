package com.kandela.mendix.cooking.xml;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class RecipeML {
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<Recipe> recipe;
  private double version;
  private String text;

  public List<Recipe> getRecipe() {
    return recipe;
  }

  public void setRecipe(List<Recipe> recipe) {
    this.recipe = recipe;
  }

  public double getVersion() {
    return version;
  }

  public void setVersion(double version) {
    this.version = version;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
