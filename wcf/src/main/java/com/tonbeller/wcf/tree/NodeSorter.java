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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * orders a set of nodes in pre- or postorder according to the tree model
 * 
 * @author av
 */
public class NodeSorter {

  TreeModel model;
  Set expanded = new HashSet();


  private NodeSorter(TreeModel model) {
    this.model = model;
  }
  

  public static List preOrder(Set nodes, TreeModel model) {
    NodeSorter ns = new NodeSorter(model);
    return ns.order(nodes, true);
  }


  public static List postOrder(Set nodes, TreeModel model) {
    NodeSorter ns = new NodeSorter(model);
    return ns.order(nodes, false);
  }
  

  List order(Set nodes, boolean preOrder) {
    expandParents(nodes);
    List list = new ArrayList();
    Object[] roots = model.getRoots();
    for (int i = 0; i < roots.length; i++)
      order(roots[i], list, nodes, preOrder);
    return list;
  }
  

  void order(Object node, List list, Set nodes, boolean preOrder) {
    if (preOrder && nodes.contains(node))
      list.add(node);
    if (expanded.contains(node)) {
      Object[] children = model.getChildren(node);
      for (int i = 0; i < children.length; i++)
        order(children[i], list, nodes, preOrder);
    }
    if (!preOrder && nodes.contains(node))
      list.add(node);
  }


  void expandParents(Set nodes) {
    expanded.clear();
    for (Iterator it = nodes.iterator(); it.hasNext();) {
      Object node = it.next();
      node = model.getParent(node);
      while (node != null) {
        expanded.add(node);
        node = model.getParent(node);
      }
    }
  }

}
