/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;

public abstract class ItemSupport implements Item {
  String label;
  String image;

  public ItemSupport() {
  }

  public ItemSupport(String label) {
    this.label = label;
  }

  public ItemSupport(String label, String image) {
    this.label = label;
    this.image = image;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
