package com.kandela.mendix.cooking.xml;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Categories {
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<String> cat;

  public List<String> getCat() {
    return cat;
  }

  public void setCat(List<String> cat) {
    this.cat = cat;
  }

}
