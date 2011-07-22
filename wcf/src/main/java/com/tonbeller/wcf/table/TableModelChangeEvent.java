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

import java.util.EventObject;

/**
 * informs listeners about changes in the table model.
 * <code>identityChanged == false</code> means a soft change,
 * e.g. swapping rows - all caches will marked dirty, sorting and paging
 * will be initialized again. <code>identityChanged == true</code> means
 * that the identity of some rows or cells has changed, or the number 
 * of columns. If true, the table component resets everything including
 * the selection. 
 *  
 * @author av
 */

public class TableModelChangeEvent extends EventObject {
  boolean identityChanged;
  
  public TableModelChangeEvent(TableModel source, boolean identityChanged) {
    super(source);
    this.identityChanged = identityChanged;
  }
  
  public TableModel getTableModel() {
    return (TableModel)getSource();
  }

  public boolean isIdentityChanged() {
    return identityChanged;
  }

}
