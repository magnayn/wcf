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

import com.tonbeller.wcf.controller.RequestContext;

/**
 * SelectionModel for Tree and Table components.
 * 
 * @author av
 */
public interface SingleSelectionModel {
  /** 
   * return the only selected object or null if the selection is empty.
   * @throws IllegalStateException if the selection contains more than one element
   */
  Object getSingleSelection();
  
  /** 
   * sets the only selected element
   */
  void setSingleSelection(Object selectedObject);

  /**
   * clears the selection
   */  
  void clear();
  
  boolean isEmpty();
  
  /** 
   * true, if item is selectable. If not,  no checkbox / radio button / hyperlink will be generated
   */
  boolean isSelectable(Object item);

  /**
   * fires a SelectionChangeEvent. This is not fired automatically
   * so clients may choose when to notify the listeners (e.g. after a
   * couple of changes have been made).
   * @param context
   */
  void fireSelectionChanged(RequestContext context);
  void addSelectionListener(SelectionChangeListener l);
  void removeSelectionListener(SelectionChangeListener l);
}
