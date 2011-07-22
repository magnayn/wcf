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
 * default implementation of a TableRow
 */

public class DefaultTableRow implements TableRow {
  Object[] columns;
  private Object userData;
  public DefaultTableRow(Object[] columns) {
    this.columns = columns;
  }
  public Object getValue(int column) {
    return columns[column];
  }
  public void setUserData(Object newUserData) {
    userData = newUserData;
  }
  public Object getUserData() {
    return userData;
  }

}

