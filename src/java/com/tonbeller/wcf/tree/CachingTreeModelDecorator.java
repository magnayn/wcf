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

import org.apache.log4j.Logger;

/**
 * a caching tree model
 * @author av
 */
public class CachingTreeModelDecorator extends TreeModelDecorator {
  Object[] roots = null;
  Map getParentMap;
  Map getChildrenMap;
  Map hasChildrenMap;
  
  private static Logger logger = Logger.getLogger(CachingTreeModelDecorator.class);
  /**
   * creates a CachingTreeModel that uses HashMap for implementation
   */
  public CachingTreeModelDecorator(TreeModel decoree) {
    super(decoree);
    getParentMap = new HashMap();
    getChildrenMap = new HashMap();
    hasChildrenMap = new HashMap();
    decoree.addTreeModelChangeListener(listener);
  }

  /**
   * creates a CachingTreeModel that uses TreeMap for implementation
   */
  public CachingTreeModelDecorator(TreeModel decoree, Comparator nodeComparator) {
    super(decoree);
    getParentMap = new TreeMap(nodeComparator);
    getChildrenMap = new TreeMap(nodeComparator);
    hasChildrenMap = new TreeMap(nodeComparator);
    decoree.addTreeModelChangeListener(listener);
  }

  /**
   * invalidates the cache
   */
  TreeModelChangeListener listener = new TreeModelChangeListener() {
    public void treeModelChanged(TreeModelChangeEvent e) {
      logger.info("invalidating TreeModelCache");
      roots = null;
      getParentMap.clear();
      getChildrenMap.clear();
      hasChildrenMap.clear();
    }
  };

  public Object[] getRoots() {
    if (roots == null)
      roots = super.getRoots();
    return roots;
  }

  public boolean hasChildren(Object node) {
    Boolean b = (Boolean) hasChildrenMap.get(node);
    if (b == null) {
      b = new Boolean(super.hasChildren(node));
      hasChildrenMap.put(node, b);
    }
    return b.booleanValue();
  }

  public Object[] getChildren(Object node) {
    Object[] children = (Object[]) getChildrenMap.get(node);
    if (children == null) {
      children = super.getChildren(node);
      if (children == null)
        children = new Object[0];
      getChildrenMap.put(node, children);
      for (int i = 0; i < children.length; i++)
        getParentMap.put(children[i], node);
    }
    return children;
  }

  public Object getParent(Object node) {
    Object parent = getParentMap.get(node);
    if (parent == null && !getParentMap.containsKey(node)) {
      parent = super.getParent(node);
      getParentMap.put(node, parent);
    }
    return parent;
  }

}
