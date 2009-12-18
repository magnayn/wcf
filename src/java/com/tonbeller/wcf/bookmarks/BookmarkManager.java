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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @author av
 */
public class BookmarkManager {
  private HttpSession session;
  private Map state = Collections.EMPTY_MAP;
  private static final String WEBKEY = BookmarkManager.class.getName();

  /** session singleton */
  BookmarkManager(HttpSession session) {
    this.session = session;
  }

  public static BookmarkManager instance(HttpSession session) {
    BookmarkManager bm = (BookmarkManager) session.getAttribute(WEBKEY);
    if (bm == null) {
      bm = new BookmarkManager(session);
      session.setAttribute(WEBKEY, bm);
    }
    return bm;
  }

  /**
   * collects tht state of all Bookmarkable Session Attributes
   * @return memento object
   */
  public Object collectSessionState(int levelOfDetail) {
    Map map = new HashMap();
    for (Enumeration en = session.getAttributeNames(); en.hasMoreElements();) {
      String name = (String) en.nextElement();
      Object attr = session.getAttribute(name);
      if (attr instanceof Bookmarkable) {
        Object value = ((Bookmarkable) attr).retrieveBookmarkState(levelOfDetail);
        if (value != null)
          map.put(name, value);
      }
    }
    return map;
  }

  /**
   * restores the State of all Bookmarkable Session Atributes
   * @param memento created by collectBookmarkState()
   * @see #collectBookmarkState
   */
  public void restoreSessionState(Object memento) {
    // no bookmark state?
    if (memento == null) {
      state = Collections.EMPTY_MAP;
      return;
    }
    state = (Map) memento;
    for (Iterator it = state.keySet().iterator(); it.hasNext();) {
      String name = (String) it.next();
      restoreAttributeState(name);
    }
  }

  /**
   * restores bookmark state for a single session attribute.
   * This is used
   * for objects that were created after restoreSessionState() was called.
   * (e.g. via jsp tags on different jsp pages).
   * @param name name of the session attribute
   */
  public void restoreAttributeState(String name) {
    Object attr = session.getAttribute(name);
    if (attr instanceof Bookmarkable) {
      Object value = state.get(name);
      if (value != null)
         ((Bookmarkable) attr).setBookmarkState(value);
    }
  }
}
