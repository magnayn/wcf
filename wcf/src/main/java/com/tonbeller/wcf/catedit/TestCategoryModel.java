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

public class TestCategoryModel implements CategoryModel {

  List categories;

  public TestCategoryModel() {
    categories = new ArrayList();

    CategorySupport c1 = new CategorySupport("Category 1", "category1.png");
    c1.setEmptyAllowed(false);
    c1.setOrderSignificant(true);
    c1.setItems(makeItems("Item 1", 3));
    categories.add(c1);

    CategorySupport c2 = new CategorySupport("Category 2", "category2.png");
    c2.setEmptyAllowed(false);
    c2.setOrderSignificant(true);
    c2.setItems(makeItems("Item 2", 1));
    categories.add(c2);

    CategorySupport c3 = new CategorySupport("Category 3", "category3.png");
    c3.setEmptyAllowed(true);
    c3.setOrderSignificant(false);
    c3.setItems(makeItems("Item 3", 7));
    categories.add(c3);
  }

  public List getCategories() {
    return categories;
  }

  List makeItems(String name, int count) {
    List list = new ArrayList();
    for (int i = 0; i < count; i++) {
      final String itemName = name + "[" + i + "]";
      Item item = new Item() {
        public String getLabel() {
          return itemName;
        }
        public boolean isMovable() {
          return true;
        }
      };
      list.add(item);
    }
    return list;
  }

  public void addCategoryModelChangeListener(CategoryModelChangeListener l) {
  }

  public void removeCategoryModelChangeListener(CategoryModelChangeListener l) {
  }

}
