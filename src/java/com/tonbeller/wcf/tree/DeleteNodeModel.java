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

import java.util.Set;

/**
 * allows the user to delete nodes from the tree model
 * 
 * @author av
 */
public interface DeleteNodeModel {
  /**
   * if true, the node will be rendered with a delete button
   */
  boolean isDeletable(Object node);

  /**
   * called when the user presses the delete button.
   * @param node
   */
  void delete(Object node);
  
  /**
   * the tree does not paint nodes that are contained in this set.
   * This allows the application to collect the deleted nodes
   * in this set and perform the actual deletion when the user
   * presses the "commit" button.
   * 
   * @return Set containing nodes not to be painted
   */
  Set getDeleted();
}
