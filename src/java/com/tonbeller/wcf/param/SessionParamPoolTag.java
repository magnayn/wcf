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
package com.tonbeller.wcf.param;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * ensures the SessionParamPool exists
 * @author av
 */
public class SessionParamPoolTag extends TagSupport {
  public int doStartTag() throws JspException {
    SessionParamPool.instance(pageContext);
    return super.doStartTag();
  }

}
