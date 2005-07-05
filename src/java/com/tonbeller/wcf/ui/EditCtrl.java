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
package com.tonbeller.wcf.ui;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.format.FormatException;
import com.tonbeller.wcf.format.FormatHandler;
import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.utils.XoplonNS;

/** 
 * has a value, type and format string. The value may be read or written into
 * a bean via model reference. 
 */
public abstract class EditCtrl extends TypedCtrl {

  /** Set value */
  public static void setValue(Element element, String value) {
    XoplonNS.setAttribute(element, "value", value);
  }

  /** Get value */
  public static String getValue(Element element) {
    return XoplonNS.getAttribute(element, "value");
  }

  /** Set max input length  */
  public static void setMaxLength(Element element, int maxLength) {
    XoplonNS.setAttribute(element, "maxlength", String.valueOf(maxLength));
  }

  /** Get max input length  */
  public static int getMaxLength(Element element) {
    return Integer.parseInt(XoplonNS.getAttribute(element, "maxlength"));
  }

  /** factory function. creates new DOM element */
  protected static Element createValueHolder(Document doc, String tagName, String type) {
    Element retVal = XoplonCtrl.createCtrl(doc, tagName);
    XoplonNS.setAttribute(retVal, "type", type);
    return retVal;
  }

  /** factory function. creates new DOM element */
  protected static Element createValueHolder(Document doc, String tagName) {
    return createValueHolder(doc, tagName, "string");
  }

  /** set value with type conversion */
  public static void setValue(Formatter formatter, Element element, Object value) throws FormatException {
    FormatHandler formatHandler = formatter.getHandler(getType(element));
    String formatString = getFormatString(element);
    String valueString = formatHandler.format(value, formatString);
    XoplonNS.setAttribute(element, "value", valueString);
  }

  /** get value with type conversion */
  public static Object getValue(Formatter formatter, Element element) throws FormatException {
    String className = getType(element);
    FormatHandler parser = formatter.getHandler(className);
    String formatString = getFormatString(element);
    return parser.parse(XoplonNS.getAttribute(element, "value"), formatString);
  }
  public static boolean getBoolean(Formatter fmt, Element elem) throws FormatException {
    return ((Boolean)getValue(fmt, elem)).booleanValue();
  }
  public static int getInt(Formatter fmt, Element elem) throws FormatException {
    return ((Number)getValue(fmt, elem)).intValue();
  }
  public static double getDouble(Formatter fmt, Element elem) throws FormatException {
    return ((Number)getValue(fmt, elem)).doubleValue();
  }
  public static Date getDate(Formatter fmt, Element elem) throws FormatException {
    return (Date)getValue(fmt, elem);
  }
  public static String getString(Formatter fmt, Element elem) throws FormatException {
    return getValue(elem);
  }

  public static void setBoolean(Formatter fmt, Element elem, boolean value) throws FormatException {
    setValue(fmt, elem, new Boolean(value));
  }
  public static void setInt(Formatter fmt, Element elem, int value) throws FormatException {
    setValue(fmt, elem, new Integer(value));
  }
  public static void setDouble(Formatter fmt, Element elem, double value) throws FormatException {
    setValue(fmt, elem, new Double(value));
  }
  public static void setDate(Formatter fmt, Element elem, Date value) throws FormatException {
    setValue(fmt, elem, value);
  }
  public static void setString(Formatter fmt, Element elem, String value) throws FormatException {
    setValue(elem, value);
  }
}
