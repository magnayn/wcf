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

/**
 * @author av
 */
public class TableModelDecorator implements TableModel, TableModelChangeListener {
  private TableModel decoree;

  protected TableModelDecorator() {
  }

  public TableModelDecorator(TableModel decoree) {
    this.decoree = decoree;
    decoree.addTableModelChangeListener(this);
  }

  public TableModel getDecoree() {
    return decoree;
  }

  public void setDecoree(TableModel newDecoree) {
    if (decoree != null)
      decoree.removeTableModelChangeListener(this);
    decoree = newDecoree;
    decoree.addTableModelChangeListener(this);
  }

  public void addTableModelChangeListener(TableModelChangeListener listener) {
    decoree.addTableModelChangeListener(listener);
  }

  public void fireModelChanged(boolean identityChanged) {
    decoree.fireModelChanged(identityChanged);
  }

  public int getColumnCount() {
    return decoree.getColumnCount();
  }

  public String getColumnTitle(int columnIndex) {
    return decoree.getColumnTitle(columnIndex);
  }

  public TableRow getRow(int rowIndex) {
    return decoree.getRow(rowIndex);
  }

  public int getRowCount() {
    return decoree.getRowCount();
  }

  public String getTitle() {
    return decoree.getTitle();
  }

  public void removeTableModelChangeListener(TableModelChangeListener listener) {
    decoree.removeTableModelChangeListener(listener);
  }

  public void tableModelChanged(TableModelChangeEvent event) {
  }

}
