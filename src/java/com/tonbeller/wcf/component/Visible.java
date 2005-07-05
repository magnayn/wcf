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
package com.tonbeller.wcf.component;

/**
 * Components that implement this interface will not be rendered if 
 * <code>isVisible</code> returns false.
 */

public interface Visible {
  boolean isVisible();
  void setVisible(boolean visible);
}
