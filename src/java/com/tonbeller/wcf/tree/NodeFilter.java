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
 * Selects the visible nodes
 */
public interface NodeFilter {
  /**
   * accept() == true for all nodes
   */
  public static final NodeFilter TRUE_FILTER = new NodeFilter() {
    public boolean accept(Object node) {
      return true;
    }
  };

  /**
   * accept() == false for all nodes
   */
  public static final NodeFilter FALSE_FILTER = new NodeFilter() {
    public boolean accept(Object node) {
      return false;
    }
  };
  
  /**
   * @return true if the node shall be visible in the tree component
   */
  boolean accept(Object node);
}
