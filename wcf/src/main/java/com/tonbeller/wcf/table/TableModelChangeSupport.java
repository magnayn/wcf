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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author av
 */
public class TableModelChangeSupport {
  ArrayList listeners = new ArrayList();
  TableModel source;

  public TableModelChangeSupport(TableModel source) {
    this.source = source;
  }

  public void fireModelChanged(boolean identityChanged) {
    if (listeners.size() > 0) {
      TableModelChangeEvent event = new TableModelChangeEvent(source, identityChanged);
      List copy = (List) listeners.clone();
      for (Iterator it = copy.iterator(); it.hasNext();)
         ((TableModelChangeListener) it.next()).tableModelChanged(event);
    }
  }

  public void addTableModelChangeListener(TableModelChangeListener l) {
    listeners.add(l);
  }

  public void removeTableModelChangeListener(TableModelChangeListener l) {
    listeners.remove(l);
  }

}
