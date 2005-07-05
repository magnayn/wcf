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
package com.tonbeller.wcf.selection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tonbeller.wcf.controller.RequestContext;

public class SelectionChangeSupport {

  ArrayList listeners = new ArrayList();
  SelectionModel source;

  public SelectionChangeSupport(SelectionModel source) {
    this.source = source;
  }

  public void fireSelectionChanged(RequestContext context) {
    if (listeners.size() > 0) {
      SelectionChangeEvent event = new SelectionChangeEvent(context, source);
      List copy = (List) listeners.clone();
      for (Iterator it = copy.iterator(); it.hasNext();)
         ((SelectionChangeListener) it.next()).selectionChanged(event);
    }
  }

  public void addSelectionListener(SelectionChangeListener l) {
    listeners.add(l);
  }

  public void removeSelectionListener(SelectionChangeListener l) {
    listeners.remove(l);
  }

}
