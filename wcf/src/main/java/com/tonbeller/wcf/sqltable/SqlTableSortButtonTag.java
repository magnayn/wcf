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
import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.scroller.Scroller;

/**
 * @author av
 * @since Oct 6, 2004
 */
public class SqlTableSortButtonTag extends SqlTableNestedTag {
  String column;
  private static final Logger logger = Logger.getLogger(SqlTableSortButtonTag.class);

  static class SortButtonHandler implements RequestListener {
    private String column;
    private boolean descending;
    private SqlTable table;

    SortButtonHandler(SqlTable table, String column, boolean descending) {
      this.table = table;
      this.column = column;
      this.descending = descending;
    }

    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      table.setOrderBy(column);
      table.setDescending(descending);
    }
  }

  public int doStartTag() throws JspException {
    try {
      if (!isRenderActions())
        return SKIP_BODY;
      
      SqlTable table = getTable();
      if (table.getOrderBy() == null)
        throw new JspException("sqlTable must have the orderBy attribute set to use sort buttons");
      RequestListener rl = getSortButtonHandler(table);
      String name = table.nextId();
      table.addRequestListener(name, null, rl);

      String imgSrc = getSortButtonImageSource(table);
      Object[] args = { name, imgSrc};
      Resources res = getResources();
      String imgTag = res.getString("sqltable.sort.imgtag", args);

      pageContext.getOut().print(imgTag);
    } catch (UnsupportedEncodingException e) {
      logger.error(null, e);
    } catch (MissingResourceException e) {
      logger.error(null, e);
    } catch (IOException e) {
      logger.error(null, e);
    }

    return SKIP_BODY;
  }

  private RequestListener getSortButtonHandler(SqlTable table) {
    String currentColumn = table.getOrderBy();
    // click on the current column changes sort direction
    if (currentColumn.equals(column))
      return new SortButtonHandler(table, column, !table.isDescending());
    // click on other column does not change direction
    return new SortButtonHandler(table, column, table.isDescending());
  }

  private String getSortButtonImageSource(SqlTable table) throws JspException {
    Resources res = getResources();
    String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
    String currentColumn = table.getOrderBy();
    if (currentColumn == null)
      throw new JspException("sqlTable must have the orderBy attribute set to use sort buttons");
    if (currentColumn.equals(column)) {
      if (table.isDescending())
        return res.getString("sqltable.sortimg.cur.desc", contextPath);
      return res.getString("sqltable.sortimg.cur.asc", contextPath);
    }
    if (table.isDescending())
      return res.getString("sqltable.sortimg.other.desc", contextPath);
    return res.getString("sqltable.sortimg.other.asc", contextPath);
  }

  public void setColumn(String column) {
    this.column = column;
  }

}
