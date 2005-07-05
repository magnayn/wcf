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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.expr.ExprUtils;
import com.tonbeller.wcf.scroller.Scroller;

/**
 * @author av
 * @since Oct 6, 2004
 */
public class SqlTablePageNavigationsTag extends SqlTableNestedTag {
  String resultExpr;
  private static Logger logger = Logger.getLogger(SqlTablePageNavigationsTag.class);

  static abstract class PageButton implements RequestListener {
    protected SqlTable table;

    protected abstract int getStartRow(RequestContext context);

    PageButton(SqlTable table) {
      this.table = table;
    }

    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      int maxRows = table.getMaxRows();
      if (maxRows < 0) {
        // show all rows
        table.setStartRow(0);
        return;
      }
      int startRow = getStartRow(context);
      if (startRow < 0)
        startRow = 0;
      table.setStartRow(startRow);
    }
  }

  static class PrevButton extends PageButton {
    public PrevButton(SqlTable table) {
      super(table);
    }

    protected int getStartRow(RequestContext context) {
      return table.getStartRow() - table.getMaxRows();
    }
  }

  static class NextButton extends PageButton {
    public NextButton(SqlTable table) {
      super(table);
    }

    protected int getStartRow(RequestContext context) {
      return table.getStartRow() + table.getMaxRows();
    }
  }

  static class GotoButton extends PageButton {
    public GotoButton(SqlTable table) {
      super(table);
    }

    protected int getStartRow(RequestContext context) {
      String value = context.getParameter(table.getId() + ".goto.value");
      if (value == null)
        return 0;
      int page;
      try {
        page = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        return 0;
      }
      return (page - 1) * table.getMaxRows();
    }
  }

  static class PageSizeButton implements RequestListener {
    private SqlTable table;

    PageSizeButton(SqlTable table) {
      this.table = table;
    }

    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      String value = context.getParameter(table.getId() + ".size.value");
      if (value == null)
        return;
      int size;
      try {
        size = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        return;
      }
      if (size > 0) {
        table.setMaxRows(size);
        table.setStartRow(0);
      }
    }

  }

  public int doStartTag() throws JspException {
    if (!isRenderActions())
      return SKIP_BODY;
    
    SqlTable table = getTable();
    int startRow = table.getStartRow();
    int maxRows = table.getMaxRows();
    if (maxRows <= 0)
      return SKIP_BODY;

    Resources res = getResources();
    String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
    String tableId = table.getId();
    String curPage = Integer.toString(startRow / maxRows + 1);
    String pageSize = Integer.toString(maxRows);
    Object[] args = { contextPath, tableId, curPage, pageSize};

    boolean havePrevious = false;
    String prevButton = "";
    if (table.getStartRow() > 0) {
      prevButton = res.getString("sqltable.page.button.prev", args);
      RequestListener r = new PrevButton(table);
      table.addRequestListener(table.getId() + ".prev", null, r);
      havePrevious = true;
    }

    boolean haveNext = false;
    String nextButton = "";
    Result result = (Result) ExprUtils.getModelReference(pageContext, resultExpr);
    if (result.isLimitedByMaxRows()) {
      nextButton = res.getString("sqltable.page.button.next", args);
      RequestListener r = new NextButton(table);
      table.addRequestListener(table.getId() + ".next", null, r);
      haveNext = true;
    }

    RequestListener r = new GotoButton(table);
    table.addRequestListener(table.getId() + ".goto.button", null, r);
    r = new PageSizeButton(table);
    table.addRequestListener(table.getId() + ".size.button", null, r);

    String show = res.getString("sqltable.page.show", args);
    String go = res.getString("sqltable.page.goto", args);
    String size = res.getString("sqltable.page.size", args);
    try {
      JspWriter out = pageContext.getOut();
      if (havePrevious) {
        out.print(prevButton);
        out.print(" ");
      }
      if (havePrevious || haveNext) {
        out.print(show);
        out.print(" ");
      }
      if (haveNext) {
        out.print(nextButton);
        out.print(" ");
      }
      if (havePrevious || haveNext) {
        out.print(go);
        out.print(" ");
      }
      pageContext.getOut().print(size);
    } catch (IOException e) {
      logger.error(null, e);
    }
    return SKIP_BODY;
  }

  public void setResult(String result) {
    this.resultExpr = result;
  }
}
