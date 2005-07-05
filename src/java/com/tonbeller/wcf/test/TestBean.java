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
package com.tonbeller.wcf.test;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.form.FormBean;
import com.tonbeller.wcf.form.FormComponent;
import com.tonbeller.wcf.selection.SelectionChangeEvent;
import com.tonbeller.wcf.selection.SelectionChangeListener;
import com.tonbeller.wcf.table.TableModel;
import com.tonbeller.wcf.table.TestModel;
import com.tonbeller.wcf.tree.TestTreeModel;
import com.tonbeller.wcf.tree.TreeModel;

public class TestBean implements FormBean {

  class MyTableModel extends TestModel implements SelectionChangeListener {
    public MyTableModel() {
      super.setTitle(null);
    }

    public void selectionChanged(SelectionChangeEvent event) {
      //System.out.println("table selection changed");
    }
  };

  class MyTreeModel extends TestTreeModel implements SelectionChangeListener {
    public void selectionChanged(SelectionChangeEvent event) {
      //System.out.println("tree selection changed");
    }
  };

  String stringValue = "some String";
  int intValue = 123;

  TableModel tableValue = new MyTableModel();
  TreeModel treeValue = new MyTreeModel();

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

  public TreeModel getTreeValue() {
    return treeValue;
  }

  public void testAction(RequestContext context) throws Exception {
    System.out.println("testhandler called");
  }

  /**
   * implement FormBean - this allows us to access the ui, e.g.
   * hide some input elements or change the selectionmodel of an
   * embedded tree component.
   */
  public void setFormComponent(RequestContext context, FormComponent form) {
  }
}
