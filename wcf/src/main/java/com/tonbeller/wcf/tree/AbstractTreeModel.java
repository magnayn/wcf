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


public abstract class AbstractTreeModel implements TreeModel {
  private TreeModelChangeSupport changeSupport;

  protected AbstractTreeModel() {
    changeSupport = new TreeModelChangeSupport(this);
  }

  public void fireModelChanged(boolean identityChanged) {
    changeSupport.fireModelChanged(identityChanged);
  }

  public void fireModelChanged(boolean identityChanged, Object parent) {
    changeSupport.fireModelChanged(identityChanged, parent);
  }

  public void fireModelChanged() {
    changeSupport.fireModelChanged(false);
  }

  public void addTreeModelChangeListener(TreeModelChangeListener l) {
    changeSupport.addTreeModelChangeListener(l);
  }

  public void removeTreeModelChangeListener(TreeModelChangeListener l) {
    changeSupport.removeTreeModelChangeListener(l);
  }

}
