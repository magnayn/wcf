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
package com.tonbeller.wcf.pagestack;

import java.util.Iterator;
import java.util.Stack;

import javax.servlet.http.HttpSession;

/**
 * @author av
 */
public class PageStack {
  public static final String WEBKEY = "wcfPageStack";
  Stack stack = new Stack();
  
  // test only
  PageStack() {
  }

  public static synchronized PageStack instance(HttpSession session) {
    PageStack ps = (PageStack) session.getAttribute(WEBKEY);
    if (ps == null) {
      ps = new PageStack();
      session.setAttribute(WEBKEY, ps);
    }
    return ps;
  }

  public void setCurrentPage(Page page) {
    while (stack.contains(page)) 
      stack.pop();
    stack.push(page);
  }

  /**
   * returns the n-th Page. 
   * @param n nuber of pages back (0 = current page, 1 = previous page etc)
   * @return null if n > number of pages, Page else.
   */
  public Page peek(int n) {
    // reverse order
    n = stack.size() - n - 1;
    if (n < 0)
      return null;
    return (Page)stack.get(n);
  }
  
  public Iterator iterator() {
    return stack.iterator();
  }

  public void clear() {
    stack.clear();
  }

}