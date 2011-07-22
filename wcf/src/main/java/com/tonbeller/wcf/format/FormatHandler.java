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
package com.tonbeller.wcf.format;

import java.util.List;
import java.util.Locale;


/**
 * defines a parser/printer for user input/display
 */
public interface FormatHandler {
  /**
   * sets the Locale that this handler should use
   */
  public void setLocale(Locale locale);

  /**
   * returns the name of the FormatHandler. Example "string" or "date".
   */
  public String getName();

  /**
   * format an object
   * @param o the object to format
   * @param pattern the format string to use. If null or empty string,
   * the configured pattern for the locale will be used.
   */
  public String format(Object o, String pattern);

  /**
   * parse a String and create an object
   * @param s the string, eg user input
   * @param pattern the format string to use. If null or empty string,
   * the configured pattern for the locale will be used.
   */
  public Object parse(String s, String pattern) throws FormatException;
  
  /**
   * returns true if this handler can handle the given object
   */
  boolean canHandle(Object value);
  
  /**
   * returns a native array of the data type. E.g., the int handler will return
   * an int[] array.
   * @param list contains wrapper objects (e.g. Integer)
   */
  Object toNativeArray(List list);

  /**
   * returns an array of wrapper objects. E.g., the int handler will return
   * an Integer[] array containing Integer instances.
   * @param value either a scalar wrapper object (e.g. Integer) or an array of native types (e.g. int[])
   * @return an array of wrapper types, e.g. Integer[]. 
   * If <code>value</code> is a scalar, the returned array 
   * contains <code>value</code> as its only element. 
   */
  Object[] toObjectArray(Object value);
  
}