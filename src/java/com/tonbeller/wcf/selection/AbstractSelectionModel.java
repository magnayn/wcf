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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * simplifies implementation of the SelectionModel
 * 
 * @author av
 */
public abstract class AbstractSelectionModel implements SelectionModel {
  private int mode;
  private SelectionChangeSupport changeSupport;

  public abstract Set getSelection();
  public abstract void add(Object obj);
  public abstract void remove(Object obj);
  public abstract void clear();

  protected AbstractSelectionModel(int mode) {
    this.mode = mode;
    changeSupport = new SelectionChangeSupport(this);
  }

  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }


  public void addAll(Collection c) {
    for (Iterator it = c.iterator(); it.hasNext();)
      add(it.next());
  }

  public boolean contains(Object obj) {
    return getSelection().contains(obj);
  }

  public Object getSingleSelection() {
    Set selection = getSelection();
    if (selection.size() == 0)
      return null;
    if (selection.size() == 1)
      return selection.iterator().next();
    throw new IllegalStateException("single selection contains " + selection.size() + " elements");
  }

  public void setSingleSelection(Object obj) {
    clear();
    add(obj);
  }

  public boolean isEmpty() {
    return getSelection().isEmpty();
  }

  /**
   * returns true for all objects except Unselectable
   * @see Unselectable
   */
  public boolean isSelectable(Object item) {
    if (item instanceof Unselectable)
      return false;
    return true;
  }

  public void fireSelectionChanged(RequestContext context) {
    changeSupport.fireSelectionChanged(context);
  }

  public void addSelectionListener(SelectionChangeListener l) {
    changeSupport.addSelectionListener(l);
  }

  public void removeSelectionListener(SelectionChangeListener l) {
    changeSupport.removeSelectionListener(l);
  }

}
