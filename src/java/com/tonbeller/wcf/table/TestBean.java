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
package com.tonbeller.wcf.table;

import com.tonbeller.wcf.selection.SelectionChangeEvent;
import com.tonbeller.wcf.selection.SelectionChangeListener;

public class TestBean {
  class MyTableModel extends TestModel implements SelectionChangeListener {
    public MyTableModel() {
      super.setTitle(null);
    }
    public void selectionChanged(SelectionChangeEvent event) {
      System.out.println("tree selection changed");
    }
  };

  String stringValue = "some String";
  int intValue = 123;

  TableModel tableValue = new MyTableModel();

  public int getIntValue() {
    return intValue;
  }

  public String getStringValue() {
    return stringValue;
  }

  public void setIntValue(int i) {
    intValue = i;
  }

  public void setStringValue(String string) {
    stringValue = string;
  }

  public TableModel getTableValue() {
    return tableValue;
  }

  public void setTableValue(TableModel model) {
    tableValue = model;
  }

}
