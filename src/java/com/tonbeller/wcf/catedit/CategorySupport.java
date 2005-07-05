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

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09.12.2002
 * 
 * @author av
 */
public class CategorySupport implements Category {
  List items = new ArrayList();
  String name;
  String icon;
  boolean orderSignificant;
  boolean emptyAllowed;
  
  /**
   * Constructor for CategorySupport.
   */
  public CategorySupport(String name, String icon) {
    this.name = name;
    this.icon = icon;
  }


  /**
   * Returns the icon.
   * @return String
   */
  public String getIcon() {
    return icon;
  }

  /**
   * Returns the items.
   * @return List
   */
  public List getItems() {
    return items;
  }

  /**
   * Returns the name.
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the icon.
   * @param icon The icon to set
   */
  public void setIcon(String icon) {
    this.icon = icon;
  }

  /**
   * Sets the items.
   * @param items The items to set
   */
  public void setItems(List items) {
    this.items = items;
  }

  /**
   * Sets the name.
   * @param name The name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the orderSignificant.
   * @return boolean
   */
  public boolean isOrderSignificant() {
    return orderSignificant;
  }

  /**
   * Sets the orderSignificant.
   * @param orderSignificant The orderSignificant to set
   */
  public void setOrderSignificant(boolean orderSignificant) {
    this.orderSignificant = orderSignificant;
  }

  /**
   * Returns the emptyAllowed.
   * @return boolean
   */
  public boolean isEmptyAllowed() {
    return emptyAllowed;
  }

  /**
   * Sets the emptyAllowed.
   * @param emptyAllowed The emptyAllowed to set
   */
  public void setEmptyAllowed(boolean emptyAllowed) {
    this.emptyAllowed = emptyAllowed;
  }

  /**
   * @see com.tonbeller.wcf.catedit.Category#addItem(Item)
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * @see com.tonbeller.wcf.catedit.Category#changeOrder(List)
   */
  public void changeOrder(List items) {
    this.items = items;
  }

  /**
   * @see com.tonbeller.wcf.catedit.Category#removeItem(Item)
   */
  public void removeItem(Item item) {
    items.remove(item);
  }

}
