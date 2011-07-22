/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;



public class MenuItemSupport extends ItemSupport implements MenuItem {
  String href;
  
  public MenuItemSupport(String href) {
    this.href = href;
  }

  public MenuItemSupport(String href, String label) {
    this(href);
    setLabel(label);
  }

  public MenuItemSupport(String href, String label, String image) {
    this(href, label);
    setImage(image);
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }


}
