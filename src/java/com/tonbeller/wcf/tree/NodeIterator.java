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
import java.util.Iterator;
import java.util.List;

/**
 * flat view of a tree model in pre- or postorder
 * 
 * @author av
 */
public class NodeIterator implements Iterator {

  private boolean preorder;

  private TreeModel tm;
  private List list = new ArrayList();
  private Iterator iter;

  /**
   * traverses the whole tree
   * @param tm
   * @param preorder
   */
  public NodeIterator(TreeModel tm, boolean preorder) {
    this.tm = tm;
    this.preorder = preorder;
    recurse(tm.getRoots());
    iter = list.iterator();
  }

  /**
   * traverses the subtree containing node and all children of node
   * @param tm
   * @param preorder
   * @param root
   */
  public NodeIterator(TreeModel tm, Object root, boolean preorder) {
    this.tm = tm;
    this.preorder = preorder;
    recurse(new Object[]{root});
    iter = list.iterator();
  }

  private void recurse(Object[] nodes) {
    for (int i = 0; i < nodes.length; i++) {
      Object node = nodes[i];
      if (preorder)
        list.add(node);
      if (tm.hasChildren(node))
        recurse(tm.getChildren(node));
      if (!preorder)
        list.add(node);
    }
  }

  public boolean hasNext() {
    return iter.hasNext();
  }

  public Object next() {
    return iter.next();
  }

  public void remove() {
    iter.remove();
  }

  public void rewind() {
    iter = list.iterator();
  }

}
