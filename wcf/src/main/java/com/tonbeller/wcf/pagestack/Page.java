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
  String relUri;
  String title;

  RequestToken requestToken;
  
  public Page(String page, String title) {
    this(page, page, page, title);
  }

  /**
   * creates a Page instance that may be added to the PageStack.
   * 
   * @param pageId identifies the page. If a page is added with a pageId that already exists in the
   * stack, the existing page and all pages above will be popped.
   * 
   * @param page the uri that will be rendered in href including the context name, e.g. /myapp/test.jsp
   * 
   * @param relUri the uri relative to the context name, e.g. /test.jsp
   * 
   * @param title the title to display
   */
  public Page(String pageId, String page, String relUri, String title) {
    if (pageId == null)
      pageId = page;
    if (relUri == null)
      relUri = page;

    this.pageId = pageId;
    this.page = page;
    this.relUri = relUri;
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

  public String getRelUri() {
    return relUri;
  }

  public void setRelUri(String relUri) {
    this.relUri = relUri;
  }

}
