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

import java.util.EventObject;

/**
 * informs listeners about changes in the tree model.
 * If identityChanged == true, then the identity of the nodes
 * have changed, which means - for example - that the selection 
 * will be cleared.
 *  
 * @author av
 */
public class TreeModelChangeEvent extends EventObject {
  boolean identityChanged;
  Object subtree;

  public TreeModelChangeEvent(TreeModel source, boolean identityChanged) {
    super(source);
    this.identityChanged = identityChanged;
  }

  public TreeModelChangeEvent(TreeModel source, Object subtree, boolean identityChanged) {
    super(source);
    this.identityChanged = identityChanged;
    this.subtree = subtree;
  }

  public TreeModel getTreeModel() {
    return (TreeModel) getSource();
  }

  public boolean isIdentityChanged() {
    return identityChanged;
  }

  /**
   * changes are restricted to the returned node and its descendants. If null, 
   * changes affect the whole tree.
   */
  public Object getSubtree() {
    return subtree;
  }
}
