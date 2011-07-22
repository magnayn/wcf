/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.tree;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A TreeModelDecorator that limits the initial visible children to a
 * enumerated set of nodes. When the user clicks on the refresh button,
 * all children are displayed from the underlying TreeModel. 
 * 
 * @author av
 * @since May 2, 2006
 */
public class EnumBoundedTreeModelDecorator extends TreeModelDecorator implements TreeBounding,
    TreeModelChangeListener {
  /** maps parent nodes to its bounded children */
  Map childrenMap;
  TreeModelChangeSupport changeSupport;

  public EnumBoundedTreeModelDecorator(TreeModel decoree) {
    super(decoree);
    childrenMap = new HashMap();
    changeSupport = new TreeModelChangeSupport(this);
    setDecoree(decoree);
  }

  public EnumBoundedTreeModelDecorator(TreeModel decoree, Comparator nodeComparator) {
    super(decoree);
    childrenMap = new TreeMap(nodeComparator);
    changeSupport = new TreeModelChangeSupport(this);
    setDecoree(decoree);
  }

  public void setDecoree(TreeModel model) {
    TreeModel decoree = getDecoree();
    if (decoree != null)
      decoree.removeTreeModelChangeListener(this);
    super.setDecoree(model);
    decoree = getDecoree();
    if (decoree != null)
      decoree.addTreeModelChangeListener(this);
  }

  public boolean isBounded(Object parent) {
    return childrenMap.containsKey(parent);
  }

  public void unbound(Object parent) {
    if (childrenMap.remove(parent) != null) {
      changeSupport.fireModelChanged(false, parent);
    }
  }

  public boolean hasChildren(Object node) {
    Object[] children = (Object[]) childrenMap.get(node);
    if (children != null)
      return true;
    return super.hasChildren(node);
  }

  public Object[] getChildren(Object node) {
    Object[] children = (Object[]) childrenMap.get(node);
    if (children != null)
      return children;
    return super.getChildren(node);
  }

  /**
   * sets the visible nodes. 
   * @param nodes the collection of visible nodes. These nodes (and their parents) 
   * will be visible before refresh.
   */
  public void setVisible(Collection nodes) {
    setVisible(getDecoree(), nodes);
  }

  /**
   * sets the visible nodes. 
   * @param model the tree model used to comupte the parents of the visible
   * nodes.
   * 
   * @param nodes the collection of visible nodes. These nodes (and their parents) 
   * will be visible before refresh.
   */
  public void setVisible(TreeModel model, Collection nodes) {
    childrenMap.clear();
    for (Iterator it = nodes.iterator(); it.hasNext();) {
      Object node = it.next();
      while (node != null) {
        Object parent = model.getParent(node);
        addVisible(parent, node);
        node = parent;
      }
    }
  }

  private void addVisible(Object parent, Object child) {
    if (parent == null || child == null)
      return;

    Object[] children = (Object[]) childrenMap.get(parent);
    if (children == null) {
      childrenMap.put(parent, new Object[] { child});
      return;
    }
    // already there?
    for (int i = 0; i < children.length; i++) {
      if (children[i].equals(child))
        return;
    }
    Object[] newChildren = new Object[children.length + 1];
    System.arraycopy(children, 0, newChildren, 0, children.length);
    newChildren[children.length] = child;
    childrenMap.put(parent, newChildren);

  }

  public void addTreeModelChangeListener(TreeModelChangeListener l) {
    changeSupport.addTreeModelChangeListener(l);
  }

  public void fireModelChanged(boolean identityChanged) {
    changeSupport.fireModelChanged(identityChanged);
  }

  public void removeTreeModelChangeListener(TreeModelChangeListener l) {
    changeSupport.removeTreeModelChangeListener(l);
  }

  public void treeModelChanged(TreeModelChangeEvent event) {
    changeSupport.fireModelChanged(event);
  }

}
