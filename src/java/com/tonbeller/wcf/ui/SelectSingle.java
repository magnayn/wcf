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
package com.tonbeller.wcf.ui;

import java.util.List;

import org.w3c.dom.Element;

/**
 * @author av
 */
public abstract class SelectSingle extends Select {

  /** get idx of selected list item */
  public static int getSelectedItemIdx(Element element) {
    List lis = getItems(element);
    for (int i = 0; i < lis.size(); ++i) {
      if (Item.isSelected((Element) lis.get(i))) {
        return i;
      }
    }
    // no list item selected
    return -1;
  }

  /** get the only selected list item.*/
  public static Element getSelectedItem(Element element) {
    List lis = getItems(element);
    for (int i = 0; i < lis.size(); ++i) {
      if (Item.isSelected((Element) lis.get(i))) {
        return (Element) lis.get(i);
      }
    }
    // no list item selected
    return null;
  }

}
