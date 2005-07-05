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
 * The data for a table
 */

public interface TableModel {
  String getTitle();
  int getRowCount();
  TableRow getRow(int rowIndex);
  int getColumnCount();
  String getColumnTitle(int columnIndex);
  
  void addTableModelChangeListener(TableModelChangeListener listener);
  void removeTableModelChangeListener(TableModelChangeListener listener);
  void fireModelChanged(boolean identityChanged);
}
