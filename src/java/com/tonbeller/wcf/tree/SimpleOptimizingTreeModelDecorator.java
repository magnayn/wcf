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
 * If the root of the tree consists of a single node and all the user
 * can do with that node is expand it, then the root is replaced
 * with its contents.
 * 
 * @author av
 */

public class SimpleOptimizingTreeModelDecorator extends TreeModelDecorator {
  NodeFilter filter;

  Object[] roots;
  boolean dirty = true;

  /**
   * creates a reduced view on a tree model that uses a HashMap for implementation
   * @param treeModel the tree model to decorate
   * @param filter accepted nodes are considered "significant"
   */
  public SimpleOptimizingTreeModelDecorator(NodeFilter filter, TreeModel decoree) {
    super(decoree);
    this.filter = filter;
    decoree.addTreeModelChangeListener(new TreeModelChangeListener() {
      public void treeModelChanged(TreeModelChangeEvent event) {
        dirty = true;
      }
    });
  }

  public Object[] getRoots() {
    if (dirty)
      initialize();
    return roots;
  }

  public Object getParent(Object node) {
    if (dirty)
      initialize();

    // its our "artificial" root, then return null
    for (int i = 0; i < roots.length; i++)
      if (roots[i].equals(node))
        return null;
    // return real parent
    return super.getParent(node);
  }

  void initialize() {
    dirty = false;
    roots = super.getRoots();
    while (roots.length == 1 && !filter.accept(roots[0]))
      roots = getChildren(roots[0]);
  }

}
