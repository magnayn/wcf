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

/**
 * implements the AND function for NodeFilter
 */
public class AndNodeFilter extends ArrayList implements NodeFilter {
  public boolean accept(Object node) {
    for (Iterator it = iterator(); it.hasNext();)
      if (!((NodeFilter) it.next()).accept(node))
        return false;
    return true;
  }
}
