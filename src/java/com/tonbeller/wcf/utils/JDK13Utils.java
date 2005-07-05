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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;

/**
 * use 1.4 functions in a 1.3 compatible way
 */
public class JDK13Utils {
  private JDK13Utils() {
  }

  private static final Class[] encodeParamTypes = new Class[]{String.class, String.class};

  /**
   * replacement for URLEncoder.encode(String s, String encoding)
   */
  public static String urlEncode(String s, String enc) throws UnsupportedEncodingException {
    try {
      // try 1.4
      Method m = URLEncoder.class.getMethod("encode", encodeParamTypes);
      return (String) m.invoke(null, new String[]{s, enc});
    } catch (NoSuchMethodException e) {
      // its 1.3
    } catch (SecurityException e) {
      throw new SoftException(e);
    } catch (IllegalArgumentException e) {
      throw new SoftException(e);
    } catch (IllegalAccessException e) {
      throw new SoftException(e);
    } catch (InvocationTargetException e) {
      Throwable x = e.getTargetException();
      if (x instanceof UnsupportedEncodingException)
        throw (UnsupportedEncodingException) x;
      throw new SoftException(e);
    }
    return URLEncoder.encode(s);
  }

  /**
   * replacement for new Locale(String lang)
   */
  public static Locale getLocale(String lang) {
    return new Locale(lang, lang.toUpperCase());
  }

  /**
   * returns the cause exception or null. Postcondition: returnvalue != e.
   * Usage pattern:
   * <pre>
   *   Throwable t = ...
   *   while (t != null) {
   *     t.printStackTrace();
   *     t = JDK13Utils.getCause(t);
   *   }
   * </pre>
   */
  public static Throwable getCause(Throwable e) {
    if (e == null)
      return null;
    Throwable prev = e;
    if (e instanceof JspException)
      e = ((JspException) e).getRootCause();
    else if (e instanceof ServletException)
      e = ((ServletException) e).getRootCause();
    else {
      // call e.getCause() in a JDK 1.3 compatible way
      try {
        Method m = e.getClass().getMethod("getCause", new Class[0]);
        e = (Throwable) m.invoke(e, new Object[0]);
      } catch (Exception ex) {
      }
    }
    if (e == prev)
      return null;
    return e;
  }
}