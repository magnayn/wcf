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
package com.tonbeller.wcf.tree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.tonbeller.wcf.changeorder.ChangeOrderModel;
import com.tonbeller.wcf.changeorder.ChangeOrderUtils;

/**
 * a caching TreeModel that allows to change the parent/child relationship.
 * @author av
 */
public class MutableTreeModelDecorator implements DecoratedTreeModel, MutableTreeModel, ChangeOrderModel {
  TreeModel decoree;

  Object[] roots;
  Object[] noChildren = new Object[] {};

  /** contains parent/child relationship, key = parent / Object, value = children / Object[] */
  Map childrenMap;

  /** contains child/parent relationship, key = child / Object, value = parent / Object */
  Map parentMap;

  /** support for ChangeOrderModel */
  boolean enableChangeOrder = true;

  TreeModelChangeSupport changeSupport;

  public MutableTreeModelDecorator(TreeModel decoree) {
    childrenMap = new HashMap();
    parentMap = new HashMap();
    changeSupport = new TreeModelChangeSupport(this);
    setDecoree(decoree);
  }

  public MutableTreeModelDecorator(TreeModel decoree, Comparator comp) {
    childrenMap = new TreeMap(comp);
    parentMap = new TreeMap(comp);
    changeSupport = new TreeModelChangeSupport(this);
    setDecoree(decoree);
  }

  /**
   * registers a changeListener with the newDecoree
   */
  public void setDecoree(TreeModel newDecoree) {
    if (decoree != null)
      decoree.removeTreeModelChangeListener(changeListener);
    decoree = newDecoree;
    decoree.addTreeModelChangeListener(changeListener);
  }

  public TreeModel getDecoree() {
    return decoree;
  }

  /**
   * if the underlying model changes, all changes are discarded
   */
  private TreeModelChangeListener changeListener = new TreeModelChangeListener() {
    public void treeModelChanged(TreeModelChangeEvent event) {
      childrenMap.clear();
      parentMap.clear();
      roots = null;
      changeSupport.fireModelChanged(event);
    }
  };

  public void change(Object parent, Object[] children) {
    if (children == null)
      children = noChildren;
    childrenMap.put(parent, children);
    for (int i = 0; i < children.length; i++)
      parentMap.put(children[i], parent);
    changeSupport.fireModelChanged(false, parent);
  }

  public Object[] getRoots() {
    if (roots == null)
      roots = decoree.getRoots();
    return roots;
  }

  public boolean hasChildren(Object node) {
    Object value = childrenMap.get(node);
    if (value == null) {
      // not cached
      boolean hasChildren = decoree.hasChildren(node);
      // if no children, don't ask again. If there are children, we wait until
      // they are really fetched. This prevents us from fetching tons of 
      // children, that are not used by the application.
      if (hasChildren)
        return true;
      childrenMap.put(node, noChildren);
      return false;
    }
    return value != noChildren;
  }

  public Object[] getChildren(Object node) {
    Object[] children = (Object[]) childrenMap.get(node);
    if (children == null) {
      children = decoree.getChildren(node);
      change(node, children);
    }
    return children;
  }

  public Object getParent(Object node) {
    Object parent = parentMap.get(node);
    if (parent != null)
      return parent;
    return decoree.getParent(node);
  }

  int indexOf(Object obj, Object[] arr) {
    for (int i = 0; i < arr.length; i++)
      if (arr[i] == obj)
        return i;
    return -1;
  }

  public boolean mayMove(Object scope, Object node) {
    return enableChangeOrder;
  }

  public void move(Object scope, Object node, int oldIndex, int newIndex) {
    Object parent = getParent(node);
    Object[] children;
    if (parent == null)
      children = getRoots();
    else
      children = getChildren(parent);
    // change order
    ChangeOrderUtils.move(children, oldIndex, newIndex);
    // save changes
    change(parent, children);
  }

  /**
   * if enabled, changeOrder will be allowed
   * @return boolean
   */
  public boolean isEnableChangeOrder() {
    return enableChangeOrder;
  }

  /**
   * if enabled, changeOrder will be allowed
   * @param enableChangeOrder The enableChangeOrder to set
   */
  public void setEnableChangeOrder(boolean enableChangeOrder) {
    this.enableChangeOrder = enableChangeOrder;
  }

  public void fireModelChanged(boolean identityChanged) {
    changeSupport.fireModelChanged(identityChanged);
  }

  public void addTreeModelChangeListener(TreeModelChangeListener l) {
    changeSupport.addTreeModelChangeListener(l);
  }

  public void removeTreeModelChangeListener(TreeModelChangeListener l) {
    changeSupport.removeTreeModelChangeListener(l);
  }

}
