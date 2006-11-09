/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.tree;

/**
 * reduces the number of children that are visible initially. When the user
 * presses the "refresh" button, all children become visible.
 * 
 * @author av
 * @since May 2, 2006
 */
public interface TreeBounding {
  /**
   * if true, instead of (+) or (-) a refresh symbol (o) 
   * is displayed to allow the user to refresh the children of 
   * the parent node.
   */
  boolean isBounded(Object parent);
  
  /**
   * called when the user presses the refresh symbol.
   */
  void unbound(Object parent);

}
