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
package com.tonbeller.wcf.utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * Includes a jsp if (and
 * only if) there is a particular http parameter in the current request.
 * <p>
 * Example usage: httpParam = "query". If a request
 * with parameter "?query=test" is received, this tag will 
 * include "/WEB-INF/test.jsp"
 */

public class IncludeTag extends TagSupport {
  String httpParam = "include";
  String prefix = "/WEB-INF/";
  String suffix = ".jsp";
  private static Logger logger = Logger.getLogger(IncludeTag.class);

  public String getHttpParam() {
    return httpParam;
  }

  public void setHttpParam(String httpParam) {
    this.httpParam = httpParam;
  }

  public int doStartTag() throws JspException {
    try {
      logger.info("enter");
      HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();

      String name = hsr.getParameter(httpParam);
      if (name != null) {
        // the parameter was specified, now we include the jsp
        String uri = prefix + name + suffix;
        logger.info("including " + uri);
        RequestDispatcher rd = pageContext.getRequest().getRequestDispatcher(uri);
        rd.include(pageContext.getRequest(), pageContext.getResponse());
      }
      logger.info("leave");
      return SKIP_BODY;
    } catch (ServletException e) {
      logger.error("exception caught", e);
      throw new JspException(e);
    } catch (IOException e) {
      logger.error("exception caught", e);
      throw new JspException(e);
    }
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

}
