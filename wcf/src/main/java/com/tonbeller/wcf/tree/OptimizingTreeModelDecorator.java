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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Hides branches that do not contain significant nodes. 
 * <p>
 * Significance is defined by a node filter. If the filter accepts a
 * node, its significant, else its not. 
 * <p>
 * Significance is transitive.  
 * A node, that is not accepted by the filter itself, is considered significant if 
 * it has at least one descendant that is accepted by the filter.
 * <p>
 * Example usecase: hide empty directories.
 * <p>
 * <b>Warning:</b> This class reads the whole tree model to find 
 * "significant" nodes.
 * 
 * @author av
 */

public class OptimizingTreeModelDecorator extends TreeModelDecorator {
  NodeFilter filter;

  Object[] roots;
  Set significant;
  boolean dirty = true;

  boolean optimizeRoot = true;
  boolean optimizeLeafs = false;
  
  /**
   * creates a reduced view on a tree model that uses a HashMap for implementation
   * @param treeModel the tree model to decorate
   * @param filter accepted nodes are considered "significant"
   */
  public OptimizingTreeModelDecorator(NodeFilter filter, TreeModel decoree) {
    super(decoree);
    this.filter = filter;
    this.significant = new HashSet();
    installChangeListener(decoree);
  }

  /**
   * creates a new optimizing tree model that uses a TreeMap for implementation
   * @param treeModel the tree model to decorate
   * @param filter accepted nodes are considered "significant"
   */
  public OptimizingTreeModelDecorator(NodeFilter filter, TreeModel decoree, Comparator nodeComparator) {
    // super(decoree, nodeComparator);
    super(decoree);
    this.filter = filter;
    this.significant = new TreeSet(nodeComparator);
    installChangeListener(decoree);
  }

  private void installChangeListener(TreeModel decoree) {
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

  void initialize() {
    dirty = false;
    significant.clear();
    recurseFindSignificant(super.getRoots());

    roots = optimize(super.getRoots());
    if (optimizeRoot) {
      while (roots.length == 1 && !filter.accept(roots[0]))
        roots = optimize(getChildren(roots[0]));
    }
  }

  boolean isSignificant(Object node) {
    return significant.contains(node);
  }

  void recurseFindSignificant(Object[] nodes) {
    for (int i = 0; i < nodes.length; i++) {
      Object node = nodes[i];
      if (super.hasChildren(node)) {
        if (filter.accept(node))
          addSignificant(node);
        recurseFindSignificant(super.getChildren(node));
      } else if (filter.accept(node))
        addSignificant(node);
      else if (!optimizeLeafs)
        significant.add(node); // leafs only
    }
  }

  private void addSignificant(Object node) {
    Object n = node;
    while (n != null) {
      significant.add(n);
      n = super.getParent(n);
    }
  }

  private Object[] optimize(Object[] nodes) {
    List optimized = new ArrayList();
    for (int i = 0; i < nodes.length; i++) {
      if (significant.contains(nodes[i]))
        optimized.add(nodes[i]);
    }
    return optimized.toArray();
  }

  public boolean hasChildren(Object node) {
    if (super.hasChildren(node))
      return getChildren(node).length > 0;
    return false;
  }

  public Object[] getChildren(Object node) {
    if (dirty)
      initialize();
    return optimize(super.getChildren(node));
  }

  public Object getParent(Object node) {
    return super.getParent(node);
  }

  /**
   * if true, only significant leafs will be visible. 
   */
  public void setOptimizeLeafs(boolean b) {
    optimizeLeafs = b;
    dirty = true;
    fireModelChanged(false);
  }

  public boolean isOptimizeLeafs() {
    return optimizeLeafs;
  }

  /**
   * if true, insignificant parents are not shown.
   */
  public void setOptimizeRoot(boolean b) {
    optimizeRoot = b;
    dirty = true;
    fireModelChanged(false);
  }

  public boolean isOptimizeRoot() {
    return optimizeRoot;
  }

}
