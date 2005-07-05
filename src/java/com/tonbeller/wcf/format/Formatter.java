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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * parse/print java objects for user input/display.
 */
public class Formatter implements BasicTypes {
  List handlerList = new ArrayList(); // order matters!
  Map handlerMap = new HashMap();
  Locale locale;

  /**
   * Constructor Formatter.
   */
  public Formatter() {
  }


  public void setLocale(Locale locale) {
    this.locale = locale;
    for (Iterator it = handlerList.iterator(); it.hasNext(); ) {
      FormatHandler fh = (FormatHandler)it.next();
      fh.setLocale(locale);
    }
  }
 
  public Locale getLocale() {
    return locale;
  }
  
  /**
   * adds a new handler for a specific data type.
   */
  public void addHandler(FormatHandler newHandler) {
    String type = newHandler.getName();
    FormatHandler oldHandler = (FormatHandler) handlerMap.get(type);
    if (oldHandler != null) {
      handlerMap.remove(type);
      handlerList.remove(oldHandler);
    }

    handlerMap.put(type, newHandler);
    handlerList.add(newHandler);    
  }

  /**
   * searches for the Format Handler that is registered for the given type
   * @param type the requested type. if null or empty string, "string" is assumed
   * @return the handler for type or null if there is no handler
   */
  public FormatHandler getHandler(String type) {
    if (type == null || type.length() == 0)
      type = BasicTypes.STRING_TYPE;
    return (FormatHandler) handlerMap.get(type);
  }

  /**
   * prints value
   */
  public String format(String type, Object value, String userPattern) {
    FormatHandler fh = getHandler(type);
    if (fh == null)
      return String.valueOf(value);
    return fh.format(value, userPattern);
  }

  /**
   * parses value into an Object
   */
  public Object parse(String type, String value, String userPattern) {
    FormatHandler fh = getHandler(type);
    if (fh == null)
      return value;
    return fh.parse(value, userPattern);
  }
  
  /**
   * returns the the first renderer that can handle the object
   */
  public FormatHandler guessHandler(Object value) {
    Iterator it = handlerList.iterator();
    while (it.hasNext()) {
      FormatHandler fh = (FormatHandler) it.next();
      if (fh.canHandle(value))
        return fh;
    }
    return null;
  }
  
 
  
}