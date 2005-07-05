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
 * exposes parent/child relationship between objects. 
 * 
 * @author av
 */
public interface TreeModel {
  
  public static final TreeModel EMPTY_MODEL = new TreeModel() {
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
  };

  /**
   * returns the root members of the tree.
   */
  Object[] getRoots();
  
  /**
   * @return true if the member can be expanded
   */
  boolean hasChildren(Object node);
  
  /**
   * @return the children of the member
   */
  Object[] getChildren(Object node);
  
  /**
   * @return the parent of member or null, if this is a root member
   */
  Object getParent(Object node);
  
  void addTreeModelChangeListener(TreeModelChangeListener l);
  void removeTreeModelChangeListener(TreeModelChangeListener l);

  
  /**
   * fires a TreeModelChangeEvent. 
   * @param identityChanged if true, the identities of the tree nodes
   * have changed and, for example, the selection will be cleared.
   */
  void fireModelChanged(boolean identityChanged);
}
