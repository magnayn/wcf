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

import javax.servlet.jsp.JspException;

import com.tonbeller.tbutils.res.Resources;

/**
 * @author av
 * @since Oct 6, 2004
 */
public class SqlTableOrderByTag extends SqlTableNestedTag {
  public int doStartTag() throws JspException {
    SqlTable table = getTable();
    String column = table.getOrderBy();
    String nested = table.getNestedOrderBy();
    Resources res = getResources();
    String sql;
    if (table.isDescending())
      sql = res.getString("sqltable.orderby.desc", column);
    else
      sql = res.getString("sqltable.orderby.asc", column);
    
    // nested == "COLUMN" or "COLUMN DESC"
    if (nested != null && !nested.startsWith(column))
      sql = sql + "," + nested;
    
    try {
      pageContext.getOut().print(sql);
    } catch (IOException e) {
      throw new JspException(e);
    }
    return super.doStartTag();
  }

}
