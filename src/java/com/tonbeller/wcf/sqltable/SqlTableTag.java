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
package com.tonbeller.wcf.sqltable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.component.RendererParameters;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * @author av
 * @since Oct 6, 2004
 */
public class SqlTableTag extends TagSupport {
  int maxRows = -1; // -1 means all
  String orderBy;
  String nestedOrderBy;
  boolean descending;
  Resources resources;
  boolean renderActions;

  public void release() {
    maxRows = -1;
    orderBy = null;
    descending = false;
    super.release();
  }

  public int doStartTag() throws JspException {
    SqlTable table = (SqlTable) pageContext.findAttribute(id);
    if (table == null) {
      table = new SqlTable();
      table.setId(id);
      table.setOrderBy(orderBy);
      table.setNestedOrderBy(nestedOrderBy);
      table.setDescending(descending);
      table.setMaxRows(maxRows);
      pageContext.setAttribute(id, table, PageContext.SESSION_SCOPE);
    }
    table.clear();
    RequestContext context = RequestContext.instance();
    resources = context.getResources(SqlTableOrderByTag.class);
    renderActions = RendererParameters.isRenderActions(context);
    return EVAL_BODY_INCLUDE;
  }

  SqlTable getTable() {
    return (SqlTable) pageContext.findAttribute(id);
  }

  Resources getResources() {
    return resources;
  }

  public void setDescending(boolean descending) {
    this.descending = descending;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public void setMaxRows(int maxRows) {
    this.maxRows = maxRows;
  }
  public void setNestedOrderBy(String nestedOrderBy) {
    this.nestedOrderBy = nestedOrderBy;
  }
  public boolean isRenderActions() {
    return renderActions;
  }
}
