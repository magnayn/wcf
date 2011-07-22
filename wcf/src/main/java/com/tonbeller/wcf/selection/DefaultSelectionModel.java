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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * default SelectionModel for Tree and Table
 * 
 * @author av
 */
public class DefaultSelectionModel implements SelectionModel {

  Set selection = new HashSet();

  int mode = MULTIPLE_SELECTION;

  SelectableFilter selectableFilter = null;

  SelectionChangeSupport changeSupport;

  public DefaultSelectionModel() {
    changeSupport = new SelectionChangeSupport(this);
  }

  public DefaultSelectionModel(int mode) {
    this.mode = mode;
    changeSupport = new SelectionChangeSupport(this);
  }

  public void setComparator(Comparator comp) {
    selection = new TreeSet(comp);
  }

  public void add(Object obj) {
    selection.add(obj);
  }

  public void remove(Object obj) {
    selection.remove(obj);
  }

  public void clear() {
    selection.clear();
  }

  public boolean contains(Object obj) {
    return selection.contains(obj);
  }

  public int getMode() {
    return mode;
  }

  public Set getSelection() {
    return Collections.unmodifiableSet(selection);
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  public void setSelection(Collection newSelection) {
    selection.clear();
    selection.addAll(newSelection);
  }

  public void addAll(Collection c) {
    this.selection.addAll(c);
  }

  /**
   * return false if item implements the tagging Unselectable interface
   * true if no SelectableFilter is set or if the SelectableFilter accepts the item.
   * 
   * @see SelectableFilter
   * @see #setSelectableFilter
   * @see #getSelectableFilter
   * @see Unselectable
   */
  public boolean isSelectable(Object item) {
    if (item instanceof Unselectable)
      return false;
    if (selectableFilter != null)
      return selectableFilter.isSelectable(item);
    return true;
  }

  public Object getSingleSelection() {
    if (selection.isEmpty())
      return null;
    if (selection.size() == 1)
      return selection.iterator().next();
    throw new IllegalStateException("getSingleSelection: selection contains " + selection.size()
        + " elements");
  }

  public void setSingleSelection(Object selectedObject) {
    selection.clear();
    selection.add(selectedObject);
  }

  /**
   * returns the current SelectableFilter
   * 
   * @see SelectableFilter
   * @see #isSelectable
   */
  public SelectableFilter getSelectableFilter() {
    return selectableFilter;
  }

  /**
   * sets the current SelectableFilter.
   * 
   * @param filter
   *          the new filter or null to accept all nodes
   * @see SelectableFilter
   * @see #isSelectable
   *  
   */
  public void setSelectableFilter(SelectableFilter filter) {
    selectableFilter = filter;
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

  public boolean isEmpty() {
    return selection.isEmpty();
  }

}