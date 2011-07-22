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
import javax.servlet.jsp.tagext.TagSupport;

import com.tonbeller.tbutils.res.Resources;

/**
 * @author av
 * @since Oct 6, 2004
 */
public abstract class SqlTableNestedTag extends TagSupport {

  protected SqlTableTag getTableTag() throws JspException {
    SqlTableTag tag = (SqlTableTag) findAncestorWithClass(this, SqlTableTag.class);
    if (tag == null)
      throw new JspException(getClass().getName() + " tag must be nested inside an SqlTable Tag");
    return tag;
  }

  protected SqlTable getTable() throws JspException {
    return getTableTag().getTable();
  }

  protected Resources getResources() throws JspException {
    return getTableTag().getResources();
  }
  
  protected boolean isRenderActions() throws JspException {
    return getTableTag().isRenderActions();
  }
}
