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

/**
 * @author av
 */
public class TreeModelDecorator implements TreeModel, DecoratedTreeModel {
  private TreeModel decoree;

  public TreeModelDecorator() {
  }

  public TreeModelDecorator(TreeModel decoree) {
    this.decoree = decoree;
  }

  public Object[] getRoots() {
    return decoree.getRoots();
  }

  public boolean hasChildren(Object node) {
    return decoree.hasChildren(node);
  }

  public Object[] getChildren(Object node) {
    return decoree.getChildren(node);
  }

  public Object getParent(Object node) {
    return decoree.getParent(node);
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

  public TreeModel getDecoree() {
    return decoree;
  }

  public void setDecoree(TreeModel model) {
    decoree = model;
  }

}
