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
package com.tonbeller.wcf.bookmarks;

/**
 * State may be saved and restored later
 * @author av
 */
public interface Bookmarkable {
  
  /**
   * The bookmark should contain as much detail as possible, even if it may not work
   * after the schema has changed.
   * See <a href="http://www.esgs.org/uk/inex.htm">http://www.esgs.org/uk/inex.htm</a>
   */
  public static final int EXTENSIONAL = 0;
  
  /**
   * The bookmark should contain a generic description which may contain less details
   * but works in all conditions.
   * See <a href="http://www.esgs.org/uk/inex.htm">http://www.esgs.org/uk/inex.htm</a>
   */
  public static final int INTENSIONAL  = 1;

  /**
   * retrieves the state of this instance
   * @param levelOfDetail INTENSIONAL or EXTENSIONAL
   */
  Object retrieveBookmarkState(int levelOfDetail);

  /**
   * sets the state of this instance
   * @param state the state returned by retrieveBookmarkState
   */
  void setBookmarkState(Object state);
}
