package com.kandela.mendix.cooking.xml;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Directions {
  private List<String> step;

  @JacksonXmlElementWrapper(useWrapping = false)
  public List<String> getStep() {
    return step;
  }

  public void setStep(List<String> step) {
    this.step = step;
  }

}
