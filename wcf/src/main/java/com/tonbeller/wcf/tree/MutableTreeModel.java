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
 * a TreeModel that allows to change the parent/child relationship.
 * 
 * @author av
 */
public interface MutableTreeModel extends TreeModel {

  /**
   * changes the parent / child relationship so that future calls to getChildren(parent) will return children
   * 
   * @param parent the parent node or null to change the root node(s)
   * 
   * @param children the new children of parent. If children is an empty array, future calls to hasChildren(parent)
   * will return true, and getChildren(parent) will return the empty array. If children is null, future calls 
   * to hasChildren(parent) will return false.
   */
  void change(Object parent, Object[] children);
  
}
