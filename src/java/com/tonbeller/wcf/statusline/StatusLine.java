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

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpSession;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.utils.JDK13Utils;
import com.tonbeller.wcf.utils.XmlUtils;

/**
 * @author av
 * @since 01.07.2004
 */
public class StatusLine {
  /** name of session attribute for jstl scripting */
  public static final String WEBKEY = "wcfStatusLine";
  private String message;
  private Exception exception;
  private String exceptionType;

  private Resources resources;

  private StatusLine() {
    resources = Resources.instance(StatusLine.class);
  }

  public static synchronized StatusLine instance() {
    return instance(RequestContext.instance().getSession());
  }
  
  public static synchronized StatusLine instance(HttpSession session) {
    StatusLine sl = (StatusLine) session.getAttribute(WEBKEY);
    if (sl == null) {
      sl = new StatusLine();
      session.setAttribute(WEBKEY, sl);
    }
    return sl;
  }

  public void setMessage(String message) {
    this.message = message;
    this.exception = null;
  }

  public void setError(String exceptionType, Exception exception) {
    this.exceptionType = exceptionType;
    this.exception = exception;
  }

  public String getStatusHTML() {
    if (exception != null)
      return getErrorHTML();
    if (message != null)
      return getMessageHTML();
    return "";
  }

  public String getStatusText() {
    if (exception != null)
      return getErrorText();
    if (message != null)
      return getMessageText();
    return "";
  }

  public boolean isEmpty() {
    return exception == null && message == null;
  }

  public void clear() {
    exception = null;
    message = null;
  }

  private String getMessageHTML() {
    String s = XmlUtils.escapeXml(message);
    s = resources.getString("statusLine.message.format.html", s);
    return s;
  }

  private String getMessageText() {
    return resources.getString("statusLine.message.format.text", message);
  }

  private String getErrorHTML() {
    return getErrorFmt("statusLine.error.format.html");
  }

  private String getErrorText() {
    return getErrorFmt("statusLine.error.format.text");
  }

  private String getErrorFmt(String resKey) {
    Object[] args = new Object[3];
    Throwable t = unwind(exception);
    if (exceptionType != null)
      args[0] = exceptionType;
    else
      args[0] = t.getClass().getName();
    args[1] = XmlUtils.escapeXml(t.getMessage());
    args[2] = getStackTrace(t);
    return resources.getString(resKey, args);
  }

  private String getStackTrace(Throwable t) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    t.printStackTrace(pw);
    pw.close();
    return sw.toString();
  }

  private Throwable unwind(Throwable t) {
    Throwable x = JDK13Utils.getCause(t);
    while (x != null) {
      t = x;
      x = JDK13Utils.getCause(t);
    }
    return t;
  }

}