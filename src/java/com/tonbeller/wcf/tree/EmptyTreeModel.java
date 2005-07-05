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
public class EmptyTreeModel implements TreeModel {
  static TreeModel model = new EmptyTreeModel();
  public static TreeModel instance() {
    return model;
  }
  
  private EmptyTreeModel() {
  }
  
  public Object[] getRoots() {
    return new Object[0];
  }

  public boolean hasChildren(Object node) {
    return false;
  }

  public Object[] getChildren(Object node) {
    return null;
  }

  public Object getParent(Object node) {
    return null;
  }

  public void addTreeModelChangeListener(TreeModelChangeListener l) {
  }

  public void removeTreeModelChangeListener(TreeModelChangeListener l) {
  }

  public void fireModelChanged(boolean identityChanged) {
  }
}
