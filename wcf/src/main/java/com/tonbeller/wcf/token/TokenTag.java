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
package com.tonbeller.wcf.token;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author av
 */
public class TokenTag extends TagSupport {

  boolean clear;

  public int doStartTag() throws JspException {
    try {
      RequestToken s = RequestToken.instance(pageContext.getSession());
      JspWriter out = pageContext.getOut();
      if (clear)
        s.setToken(null);
      else {
        out.println(
          "<input type=\"hidden\" name=\""
            + s.getHttpParameterName()
            + "\" value=\""
            + s.getToken()
            + "\" />");
      }
    } catch (IOException e) {
      throw new JspException(e);
    }
    return super.doStartTag();
  }

  public void setClear(boolean b) {
    clear = b;
  }

}
