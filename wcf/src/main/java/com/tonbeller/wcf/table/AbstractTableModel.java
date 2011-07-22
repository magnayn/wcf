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
public abstract class AbstractTableModel implements TableModel {
  private TableModelChangeSupport changeSupport;
  
  protected AbstractTableModel() {
    changeSupport = new TableModelChangeSupport(this); 
  }

  public void fireModelChanged() {
    changeSupport.fireModelChanged(false);
  }

  public void fireModelChanged(boolean identityChanged) {
    changeSupport.fireModelChanged(identityChanged);
  }

  public void addTableModelChangeListener(TableModelChangeListener l) {
    changeSupport.addTableModelChangeListener(l);
  }

  public void removeTableModelChangeListener(TableModelChangeListener l) {
    changeSupport.removeTableModelChangeListener(l);
  }

}
