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

import java.util.ArrayList;
import java.util.List;

/**
 * uses a <code>NodeFilter</code> to select the visible nodes
 */
public class FilteringTreeModelDecorator implements TreeModel, DecoratedTreeModel {
  TreeModel decoree;
  NodeFilter filter;
  
  /**
   * tree nodes that are accepted by this filter are assumed to have
   * always children. So hasChildren() returns always true and does
   * not call getChildren()
   */
  NodeFilter hasAlwaysChildren = NodeFilter.TRUE_FILTER;

  public FilteringTreeModelDecorator(TreeModel decoree, NodeFilter filter) {
    this.decoree = decoree;
    this.filter = filter;
  }

  public FilteringTreeModelDecorator(TreeModel decoree, NodeFilter filter, NodeFilter hasAlwaysChildren) {
    this.decoree = decoree;
    this.filter = filter;
    this.hasAlwaysChildren = hasAlwaysChildren;
  }
  
  public Object[] getRoots() {
    return acceptableMembers(decoree.getRoots());
  }

  public boolean hasChildren(Object node) {
    if (!decoree.hasChildren(node))
      return false;
    if(hasAlwaysChildren.accept(node))
      return true;

    return hasAcceptableMember(decoree.getChildren(node));
  }

  public Object[] getChildren(Object node) {
    return acceptableMembers(decoree.getChildren(node));
  }

  public Object getParent(Object node) {
    return decoree.getParent(node);
  }

  boolean hasAcceptableMember(Object[] array) {
    for (int i = 0; i < array.length; i++)
      if (filter.accept(array[i]))
        return true;
    return false;
  }

  Object[] acceptableMembers(Object[] array) {
    List accepted = new ArrayList();
    for (int i = 0; i < array.length; i++)
      if (filter.accept(array[i]))
        accepted.add(array[i]);
    return accepted.toArray();
  }

  public TreeModel getDecoree() {
    return decoree;
  }

  public NodeFilter getFilter() {
    return filter;
  }

  public void setDecoree(TreeModel model) {
    decoree = model;
  }

  public void setFilter(NodeFilter filter) {
    this.filter = filter;
  }

  public void addTreeModelChangeListener(TreeModelChangeListener l) {
    decoree.addTreeModelChangeListener(l);
  }

  public void removeTreeModelChangeListener(TreeModelChangeListener l) {
    decoree.removeTreeModelChangeListener(l);
  }

  public void fireModelChanged(boolean identityChanged) {
    decoree.fireModelChanged(identityChanged);
  }

  /**
   * tree nodes that are accepted by this filter are assumed to have
   * always children. So hasChildren() returns always true and does
   * not call getChildren()
   */
  public NodeFilter getHasAlwaysChildren() {
    return hasAlwaysChildren;
  }
  /**
   * tree nodes that are accepted by this filter are assumed to have
   * always children. So hasChildren() returns always true and does
   * not call getChildren()
   */
  public void setHasAlwaysChildren(NodeFilter hasAlwaysChildren) {
    this.hasAlwaysChildren = hasAlwaysChildren;
  }
}
