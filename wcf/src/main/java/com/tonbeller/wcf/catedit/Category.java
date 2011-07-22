/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.catedit;

import java.util.List;

/**
 * a category that has items associated with it
 * 
 * @author av
 */
public interface Category {

  /**
   * returns the image name to use for buttons
   */
  String getIcon();

  /**
   * returns the label
   */
  String getName();

  /**
   * returns the items of this category. The items must implement the Item interface
   * @return a read-only list of Item's
   */
  List getItems();

  /**
   * adds an item to this category
   */
  void addItem(Item item);

  /**
   * removes an item of this category
   */
  void removeItem(Item item);

  /**
   * changes the order of the items
   */
  void changeOrder(List items);

  /**
   * if order is significant, move-up and move-down buttons are generated.
   */
  boolean isOrderSignificant();

  /**
   * if true this category may be empty (has no items)
   */
  boolean isEmptyAllowed();
}
