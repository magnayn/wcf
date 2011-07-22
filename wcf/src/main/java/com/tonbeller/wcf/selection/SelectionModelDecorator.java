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
import java.util.Set;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * decorates a selection model
 * @author av
 */

public abstract class SelectionModelDecorator implements SelectionModel {
  SelectionModel delegate;

  public SelectionModelDecorator(SelectionModel delegate) {
    this.delegate = delegate;
  }

  /**
   * @param obj
   */
  public void add(Object obj) {
    delegate.add(obj);
  }

  /**
   * @param c
   */
  public void addAll(Collection c) {
    delegate.addAll(c);
  }

  /**
   * @param l
   */
  public void addSelectionListener(SelectionChangeListener l) {
    delegate.addSelectionListener(l);
  }

  /**
   * 
   */
  public void clear() {
    delegate.clear();
  }

  /**
   * @param obj
   * @return
   */
  public boolean contains(Object obj) {
    return delegate.contains(obj);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object arg0) {
    return delegate.equals(arg0);
  }

  /**
   * @param context
   */
  public void fireSelectionChanged(RequestContext context) {
    delegate.fireSelectionChanged(context);
  }

  /**
   * @return
   */
  public int getMode() {
    return delegate.getMode();
  }

  /**
   * @return
   */
  public Set getSelection() {
    return delegate.getSelection();
  }

  /**
   * @return
   */
  public Object getSingleSelection() {
    return delegate.getSingleSelection();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return delegate.hashCode();
  }

  /**
   * @param item
   * @return
   */
  public boolean isSelectable(Object item) {
    return delegate.isSelectable(item);
  }

  /**
   * @param obj
   */
  public void remove(Object obj) {
    delegate.remove(obj);
  }

  /**
   * @param l
   */
  public void removeSelectionListener(SelectionChangeListener l) {
    delegate.removeSelectionListener(l);
  }

  /**
   * @param selectedObject
   */
  public void setSingleSelection(Object selectedObject) {
    delegate.setSingleSelection(selectedObject);
  }

  public String toString() {
    return delegate.toString();
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

}
