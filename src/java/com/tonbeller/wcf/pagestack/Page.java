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

import com.tonbeller.wcf.token.RequestToken;

/**
 * @author av
 */
public class Page {
  String pageId;
  String page;
  String title;

  RequestToken requestToken;
  
  public Page() {
  }

  public Page(String page, String title) {
    this(page, page, title);
  }

  public Page(String pageId, String page, String title) {
    if (pageId == null)
      pageId = page;

    this.pageId = pageId;
    this.page = page;
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPageHref() {
    if (requestToken == null)
      return page;
    return requestToken.appendHttpParameter(page);
  }
  
  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public int hashCode() {
    return pageId.hashCode();
  }

  public boolean equals(Object o) {
    if (!(o instanceof Page))
      return false;
    Page p = (Page) o;
    return pageId.equals(p.pageId);
  }

  void setRequestToken(RequestToken requestToken) {
    this.requestToken = requestToken;
  }

}
