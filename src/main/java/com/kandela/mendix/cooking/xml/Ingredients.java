package com.kandela.mendix.cooking.xml;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Ingredients {
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<Ing> ing;

  public List<Ing> getIng() {
    return ing;
  }

  public void setIng(List<Ing> ing) {
    this.ing = ing;
  }
  
}