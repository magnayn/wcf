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
package com.tonbeller.wcf.expr;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.tonbeller.wcf.utils.SoftException;

/**
 * evaluates EL expressions, while we are waiting for servlet 2 / tomcat 5
 * @author av
 */
public class ExprUtils {
  private static Logger logger = Logger.getLogger(ExprUtils.class);

  private ExprUtils() {
    super();
  }

  public static void checkExpr(String expr) {
    if (expr == null)
      throw new IllegalArgumentException("expr is null");
    if (isExpression(expr) && !expr.endsWith("}"))
      throw new IllegalArgumentException("expr must end with \"}\"");
  }

  public static Object getModelReference(PageContext pageContext, String expr) {
    ExprContext ec = getExprContextAdapter(pageContext);
    return getModelReference(ec, expr);

  }

  public static ExprContext getExprContextAdapter(final PageContext pageContext) {
    return new ExprContext() {
      public Object findBean(String name) {
        return pageContext.findAttribute(name);
      }
      public void setBean(String name, Object bean) {
        if (bean == null)
          pageContext.removeAttribute(name);
        else
          pageContext.setAttribute(name, bean, PageContext.SESSION_SCOPE);
      }
    };
  }

  public static Object getModelReference(ExprContext context, String expr) {
    try {
      if (expr == null || expr.length() == 0)
        return null;
      // plain string?
      if (!isExpression(expr))
        return context.findBean(expr);

      if (!expr.endsWith("}"))
        throw new IllegalArgumentException("expr must end with '}'");

      // dotted expression?
      int pos = expr.indexOf('.');
      if (pos < 0) {
        // no, find attribute in context
        String name = expr.substring(2, expr.length() - 1);
        return context.findBean(name);
      }
      // yes, evaluate property path
      String name = expr.substring(2, pos);
      Object bean = context.findBean(name);
      if (bean == null)
        throw new IllegalArgumentException("bean \"" + name + "\" not found");
      String path = expr.substring(pos + 1, expr.length() - 1);
      return PropertyUtils.getProperty(bean, path);
    } catch (IllegalAccessException e) {
      logger.error("?", e);
      throw new SoftException(e);
    } catch (InvocationTargetException e) {
      logger.error("?", e);
      throw new SoftException(e);
    } catch (NoSuchMethodException e) {
      logger.error("?", e);
      throw new SoftException(e);
    }
  }

  public static void setModelReference(ExprContext context, String expr, Object value) {
    if (expr == null)
      throw new NullPointerException("expr is null");

    // plain "bean" expr?
    if (!isExpression(expr)) {
      context.setBean(expr, value);
      return;
    }

    if (!expr.endsWith("}"))
      throw new IllegalArgumentException("expr must end with '}'");

    // "#{bean}" expr?
    if (expr.indexOf('.') < 0) {
      // no bean, set session attribute
      String name = expr.substring(2, expr.length()- 1);
      context.setBean(name, value);
      return;
    }

    // "#{bean.property.path}" expr
    try {
      int pos = expr.indexOf('.');
      String name = expr.substring(2, pos);
      Object bean = context.findBean(name);
      if (bean == null)
        throw new IllegalArgumentException("bean \"" + name + "\" not found");
      String path = expr.substring(pos + 1, expr.length() - 1);
      PropertyUtils.setProperty(bean, path, value);
    } catch (IllegalAccessException e) {
      logger.error("exception caught", e);
      throw new SoftException(e);
    } catch (InvocationTargetException e) {
      logger.error("exception caught", e);
      throw new SoftException(e);
    } catch (NoSuchMethodException e) {
      logger.error("exception caught", e);
      throw new SoftException(e);
    }
  }

  public static PropertyDescriptor getPropertyDescriptor(ExprContext context, String expr) {
    if (!ExprUtils.isExpression(expr) || !expr.endsWith("}") || expr.indexOf('.') < 0)
      throw new IllegalArgumentException("'#{bean.property}' expected");

      int pos = expr.indexOf('.');
      String name = expr.substring(2, pos);
      Object bean = context.findBean(name);
      if (bean == null)
        throw new IllegalArgumentException("bean \"" + name + "\" not found");
      String path = expr.substring(pos + 1, expr.length() - 1);
      try {
        return PropertyUtils.getPropertyDescriptor(bean, path);
      } catch (Exception e) {
        logger.error(null, e);
        return null;
      }
  }

  /**
   * extract the name of the bean. For example, in the expression
   * "${bean.property}" the bean name is "bean".
   */
  public static String getBeanName(String expr) {
    if (isExpression(expr))
      expr = expr.substring(2, expr.length() - 1);
    int pos = expr.indexOf('.');
    if (pos > 0)
      expr = expr.substring(0, pos);
    return expr;
  }

  /**
   * true, if expr starts with "${" or with "#{"
   */
  public static boolean isExpression(String expr) {
    if (expr == null)
      return false;
    return expr.startsWith("${") || expr.startsWith("#{");
  }

}
