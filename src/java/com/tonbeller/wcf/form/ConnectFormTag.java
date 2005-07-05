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
package com.tonbeller.wcf.form;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.component.Form;
import com.tonbeller.wcf.controller.RequestContext;

public class ConnectFormTag extends TagSupport {
  String form1;
  String form2;
	private static Logger logger = Logger.getLogger(ConnectFormTag.class);

  public void setForm1(String string) {
    form1 = string;
  }

  public void setForm2(String string) {
    form2 = string;
  }

  public int doStartTag() throws JspException {
		logger.info("enter");
    RequestContext context = RequestContext.instance();
    Form f1 = (Form)context.getModelReference(form1); 
    if (f1 == null) {
      String err = "could not find " + form1;
      logger.error(err);
      throw new JspException(err);
    }
    Form f2 = (Form)context.getModelReference(form2); 
    if (f2 == null) {
      String err = "could not find " + form2;
      logger.error(err);
      throw new JspException(err);
    }
    f1.addFormListener(f2);
    f2.addFormListener(f1);
		logger.info("leave");
    return super.doStartTag();
  }

}
