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
package com.tonbeller.wcf.statusline;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author av
 * @since 01.07.2004
 */
public class StatusLineTag extends TagSupport {
  static final String HTML = "html";
  static final String TEXT = "text";
  private static Logger logger = Logger.getLogger(StatusLineTag.class);
  String format = "html";
  boolean clear = true;

  public int doStartTag() throws JspException {
    try {
      StatusLine sl = StatusLine.instance(pageContext.getSession());
      if (sl.isEmpty())
        return Tag.EVAL_BODY_INCLUDE;
      if (HTML.equals(format))
        pageContext.getOut().print(sl.getStatusHTML());
      else if (TEXT.equals(format))
        pageContext.getOut().print(sl.getStatusText());
      else
        throw new JspException("unknown format: " + format + " (expected \"html\" or \"text\")");
      if (clear)
        sl.clear();
      return Tag.SKIP_BODY;
    } catch (IOException e) {
      logger.error(null, e);
      return Tag.SKIP_BODY;
    }
  }

  public void setClear(boolean clear) {
    this.clear = clear;
  }

  public void setFormat(String format) {
    this.format = format;
  }
}