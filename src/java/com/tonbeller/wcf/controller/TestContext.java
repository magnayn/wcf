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
package com.tonbeller.wcf.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.fileupload.FileItem;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.convert.Converter;
import com.tonbeller.wcf.convert.ConverterFactory;
import com.tonbeller.wcf.expr.ExprContext;
import com.tonbeller.wcf.expr.ExprUtils;
import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.format.FormatterFactory;

/**
 * Created on 28.11.2002
 *
 * @author av
 */
public class TestContext extends RequestContext {

  Formatter formatter = FormatterFactory.instance(getLocale());
  Converter converter = ConverterFactory.instance(formatter);
  ExprContext exprContext;
  Map parameters = new HashMap();
  HttpSession session = new TestSession();

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getRequest()
   */
  public HttpServletRequest getRequest() {
    return null;
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getResponse()
   */
  public HttpServletResponse getResponse() {
    return null;
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getServletContext()
   */
  public ServletContext getServletContext() {
    return null;
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getSession()
   */
  public HttpSession getSession() {
    return session;
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getConverter()
   */
  public Converter getConverter() {
    return converter;
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getFormatter()
   */
  public Formatter getFormatter() {
    return formatter;
  }

  public Map getParameters() {
    return parameters;
  }

  public void setParameters(Map map) {
    this.parameters = map;
  }

  public String[] getParameters(String name) {
    return (String[])parameters.get(name);
  }

  public String getParameter(String name) {
    String[] values = getParameters(name);
    if (values != null && values.length > 0)
      return values[0];
    return null;
  }

  public Locale getLocale() {
    return Locale.US;
  }

  /**
   * Returns the exprContext.
   * @return ExprContext
   */
  public ExprContext getExprContext() {
    return exprContext;
  }

  /**
   * Sets the exprContext.
   * @param exprContext The exprContext to set
   */
  public void setExprContext(ExprContext exprContext) {
    this.exprContext = exprContext;
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#getModelReference(String)
   */
  public Object getModelReference(String expr) {
    return ExprUtils.getModelReference(exprContext, expr);
  }

  /**
   * @see com.tonbeller.wcf.controller.RequestContext#setModelReference(String, Object)
   */
  public void setModelReference(String expr, Object value) {
    ExprUtils.setModelReference(exprContext, expr, value);
  }

  public boolean isUserInRole(String roleExpr) {
    return false;
  }

  public Resources getResources() {
    return Resources.instance(getLocale());
  }

  public Resources getResources(String bundleName) {
    return Resources.instance(getLocale(), bundleName);
  }

  public Resources getResources(Class clasz) {
    return Resources.instance(getLocale(), clasz);
  }

  public String getRemoteUser() {
    return "guest";
  }

  public String getRemoteDomain() {
    return null;
  }

  public boolean isAdmin() {
    return false;
  }
  public class TestSession implements HttpSession {
    HashMap attrs = new HashMap();

    public long getCreationTime() {
      return 0;
    }

    public String getId() {
      return "testSessionID";
    }

    public long getLastAccessedTime() {
      return 0;
    }

    public ServletContext getServletContext() {
      return null;
    }

    public void setMaxInactiveInterval(int arg0) {
    }

    public int getMaxInactiveInterval() {
      return 0;
    }

    /** @deprecated */
    public javax.servlet.http.HttpSessionContext getSessionContext() {
      return null;
    }

    public Object getAttribute(String id) {
      return attrs.get(id);
    }

    /** @deprecated */
    public Object getValue(String id) {
      return getAttribute(id);
    }

    public Enumeration getAttributeNames() {
      Vector v = new Vector();
      v.addAll(attrs.keySet());
      return v.elements();
    }

    /** @deprecated */
    public String[] getValueNames() {
      return (String[]) attrs.keySet().toArray(new String[0]);
    }

    public void setAttribute(String id, Object att) {
      removeAttribute(id);
      attrs.put(id, att);
      if (att instanceof HttpSessionBindingListener) {
        HttpSessionBindingEvent e = new HttpSessionBindingEvent(this, id);
        ((HttpSessionBindingListener)att).valueBound(e);
      }
    }

    /** @deprecated */
    public void putValue(String id, Object attr) {
      setAttribute(id, attr);
    }

    public void removeAttribute(String id) {
      Object attr = attrs.get(id);
      if (attr instanceof HttpSessionBindingListener) {
        HttpSessionBindingEvent e = new HttpSessionBindingEvent(this, id);
        ((HttpSessionBindingListener)attr).valueUnbound(e);
      }
    }

    /** @deprecated */
    public void removeValue(String id) {
      removeAttribute(id);
    }

    public void invalidate() {
    }

    public boolean isNew() {
      return false;
    }
  }

  public Object findBean(String name) {
    return exprContext.findBean(name);
  }

  public void setBean(String name, Object bean) {
    exprContext.setBean(name, bean);
  }

  public void setLocale(Locale locale) {
  }

  public FileItem getFileItem(String name) {
    return null;
  }

  public Map getFileParameters() {
    return null;
  }
}
