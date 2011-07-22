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
package com.tonbeller.wcf.changeorder;


/**
 * Model for ChangeOrderMgr
 * 
 * @author av
 */
public interface ChangeOrderModel {
  
  /**
   * returns true, if node may be moved 
   */  
  boolean mayMove(Object scope, Object node);
  
  /**
   * called after the user has clicked on an item to move it.
   * The element at <code>oldIndex</code> is removed from the array, so the size
   * of the array is reduced by one. After that, the element is inserted at the
   * index <code>newIndex</code>. A valid implementation would be:
   * <pre>
   *   ArrayList al = ...
   *   Object o = al.remove(oldIndex);
   *   al.add(newIndex, o);
   * </pre>
   * 
   * @param scope the scope for the node to move (for convenience).
   * @param item the node to move (for convenience), which lives at position oldIndex
   * @param oldIndex the index of <code>item</code> before it was moved
   * @param newIndex the index of <code>item</code> after it has been moved
   * 
   * @see ChangeOrderUtils
   */
  void move(Object scope, Object item, int oldIndex, int newIndex);
}
