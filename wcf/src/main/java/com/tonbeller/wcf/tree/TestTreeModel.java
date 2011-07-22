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

import org.apache.log4j.Logger;

/**
 * Created on 10.12.2002
 * 
 * @author av
 */
public class TestTreeModel extends AbstractTreeModel {
  private static final Logger logger = Logger.getLogger(TestTreeModel.class);
  private int[] childCount;
  Node[] roots;

  public TestTreeModel() {
    this(new int[] { 3, 4, 4, 4, 4, 4});
  }

  public TestTreeModel(int[] childCount) {
    this.childCount = childCount;

    char c = 'A';
    roots = new Node[childCount[0]];
    for (int i = 0; i < roots.length; i++) {
      roots[i] = new Node(null, "" + (char) (c + i), 1);
    }
  }

  public class Node {

    Node parent;
    String name;
    int level;
    Node[] children;

    public Node(Node parent, String name, int level) {
      this.parent = parent;
      this.name = name;
      this.level = level;
      if (level < childCount.length) {
        children = new Node[childCount[level]];
        for (int i = 0; i < children.length; i++) {
          children[i] = new Node(this, name + "[" + i + "]", level + 1);
        }
      }
    }

    boolean hasChildren() {
      return children != null;
    }

    Object[] getChildren() {
      return children;
    }

    public int getLevel() {
      return level;
    }

    public String getName() {
      return name;
    }

    public void setLevel(int level) {
      this.level = level;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }

    public String toString() {
      return name;
    }
  }

  public Object[] getRoots() {
    logger.info("getRoots");
    return roots;
  }

  public boolean hasChildren(Object node) {
    logger.info("hasChildren: " + node);
    return ((Node) node).hasChildren();
  }

  public Object[] getChildren(Object node) {
    logger.info("getChildren: " + node);
    return ((Node) node).getChildren();
  }

  public Object getParent(Object node) {
    logger.info("getParent: " + node);
    return ((Node) node).getParent();
  }

  public DeleteNodeModel getDeleteNodeModel() {
    NodeFilter nf = new NodeFilter() {
      public boolean accept(Object node) {
        return ((Node) node).getName().startsWith("A");
      }
    };
    DefaultDeleteNodeModel ddnm = new DefaultDeleteNodeModel();
    ddnm.setDeletableFilter(nf);
    return ddnm;
  }
}