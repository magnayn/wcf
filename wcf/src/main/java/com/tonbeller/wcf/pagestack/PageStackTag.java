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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTagSupport;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.expr.ExprUtils;
import com.tonbeller.wcf.token.RequestToken;

/**
 * @author av
 */
public class PageStackTag extends LoopTagSupport {
  String page;
  String pageId;
  String title;
  String key;
  boolean clear;
  String bundle;

  Iterator iter;

  protected void prepare() throws JspTagException {
    String s = makeTitle();
    PageStack stack = PageStack.instance(pageContext.getSession());
    if (clear)
      stack.clear();
    if (page != null && s != null) {
      String path = addContextPath(page);
      Page curPg = new Page(pageId, path, page, s);
      curPg.setRequestToken(RequestToken.instance(pageContext.getSession()));
      stack.setCurrentPage(curPg);
    }
    iter = stack.iterator();
  }

  private String addContextPath(String path) {
    // prepend context
    if (path.startsWith("/")) {
      HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
      path = hsr.getContextPath() + path;
    }
    return path;
  }

  protected Object next() throws JspTagException {
    return iter.next();
  }

  protected boolean hasNext() throws JspTagException {
    return iter.hasNext();
  }
  
  private String makeTitle() {
    // use resbundle?
    if (key == null)
      return eval(title);
    
    // bundle given?
    if (bundle != null) {
      RequestContext context = RequestContext.instance();
      Resources res = context.getResources(bundle);
      return res.getOptionalString(key, key);
    }
    
    // JSTL Locale support
    return LocaleSupport.getLocalizedMessage(pageContext, key);
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setClear(boolean clear) {
    this.clear = clear;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setBundle(String bundle) {
    this.bundle = bundle;
  }
  
  private String eval(String el) {
    if (ExprUtils.isExpression(el))
      return String.valueOf(ExprUtils.getModelReference(pageContext, el));
    return el;
  }

}