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
import java.util.Iterator;
import java.util.List;

public class TreeModelChangeSupport {
  TreeModel source;
  ArrayList listeners = new ArrayList();

  public TreeModelChangeSupport(TreeModel source) {
    this.source = source;
  }

  public void fireModelChanged(boolean identityChanged, Object root) {
    if (listeners.size() > 0) {
      TreeModelChangeEvent event = new TreeModelChangeEvent(source, root, identityChanged);
      List copy = (List) listeners.clone();
      for (Iterator it = copy.iterator(); it.hasNext();)
        ((TreeModelChangeListener) it.next()).treeModelChanged(event);
    }
  }

  public void fireModelChanged(TreeModelChangeEvent event) {
    fireModelChanged(event.isIdentityChanged(), event.getSubtree());
  }

  public void fireModelChanged(boolean identityChanged) {
    fireModelChanged(identityChanged, null);
  }

  public void addTreeModelChangeListener(TreeModelChangeListener l) {
    listeners.add(l);
  }

  public void removeTreeModelChangeListener(TreeModelChangeListener l) {
    listeners.remove(l);
  }

}
