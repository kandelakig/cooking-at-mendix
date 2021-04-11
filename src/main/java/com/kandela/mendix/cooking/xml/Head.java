package com.kandela.mendix.cooking.xml;

public class Head {
  private String title;
  private Categories categories;
  private int yield;
  private String timeNeeded;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Categories getCategories() {
    return categories;
  }

  public void setCategories(Categories categories) {
    this.categories = categories;
  }

  public int getYield() {
    return yield;
  }

  public void setYield(int yield) {
    this.yield = yield;
  }

  public String getTimeNeeded() {
    return timeNeeded;
  }

  public void setTimeNeeded(String timeNeeded) {
    this.timeNeeded = timeNeeded;
  }

}
