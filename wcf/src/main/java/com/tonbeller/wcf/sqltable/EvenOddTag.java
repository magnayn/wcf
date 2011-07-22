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
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.jstl.core.LoopTagStatus;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * appends "even" or "odd" depending on the index of the sorrounding
 * loop tag.
 * @author av
 */
public class EvenOddTag extends TagSupport {
  String clazz;
  String even;
  String odd;

  private static final Logger logger = Logger.getLogger(EvenOddTag.class);

  public int doStartTag() throws JspException {
    LoopTag tag = (LoopTag) super.findAncestorWithClass(this, LoopTag.class);
    if (tag == null)
      throw new JspException("must be nested in a loop tag");
      
    LoopTagStatus status = tag.getLoopStatus();
    String s;
    if (status.getCount() % 2 == 0)
      s = (clazz == null) ? even : clazz + "-even";
    else
      s = (clazz == null) ? odd : clazz + "-odd";
    try {
      pageContext.getOut().print(s);
    } catch (IOException e) {
      logger.error(null, e);
    }

    return super.doStartTag();
  }

  public void setClazz(String clazz) {
    this.clazz = clazz;
  }

  public void setEven(String even) {
    this.even = even;
  }
  public void setOdd(String odd) {
    this.odd = odd;
  }
}
