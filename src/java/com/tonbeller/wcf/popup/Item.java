/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;


public interface Item {
  /**
   * @return image of the menu entry or null. If image starts with "/" then
   * the context will be prepended.
   */
  String getImage();

  /**
   * @return visible text of the menuitem text or null
   */
  String getLabel();
}
