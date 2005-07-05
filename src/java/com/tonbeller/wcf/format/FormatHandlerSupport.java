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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


/**
 * base class for FormatHandlers
 */
public abstract class FormatHandlerSupport implements FormatHandler {
  protected String name;
  protected String pattern;
  protected String errorMessage;
  protected ArrayList patterns = new ArrayList();
  protected Locale locale;

  /**
   * adds a pattern for a specific locale
   */
  public void addPattern(Pattern p) {
    patterns.add(p);
  }

  /**
   * returns the pattern for a given locale. First it checks if a Pattern child
   * exists if a child exists with the same language as locale. If not, the pattern
   * property of this is returned.
   * @param userPattern a pattern that the user may have provided. If not null 
   * and not empty, the userPattern will be returned.
   */
  protected String findPattern(String userPattern) {
    
    if (userPattern != null && userPattern.length() > 0)
      return userPattern;
      
    Iterator it = patterns.iterator();

    while (it.hasNext()) {
      Pattern p = (Pattern) it.next();

      if (locale.getLanguage().equals(p.getLanguage())) {
        return p.getPattern();
      }
    }

    return this.getPattern();
  }

  protected String getErrorMessage(String userInput) {
    String errorMessage = null;

    Iterator it = patterns.iterator();

    while (it.hasNext()) {
      Pattern p = (Pattern) it.next();

      if (locale.getLanguage().equals(p.getLanguage())) {
        errorMessage = p.getErrorMessage();
      }
    }

    // no locale specific error message found?
    if (errorMessage == null) {
      errorMessage = getErrorMessage();
    }

    if (errorMessage == null) {
      return userInput;
    }

    return MessageFormat.format(errorMessage, new Object[] { userInput });
  }

  /**
   * Returns the name.
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the default pattern
   * @return String
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Sets the name.
   * @param name The name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the default pattern.
   * @param pattern The pattern to set
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * Returns the errorMessage.
   * @return String
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Sets the errorMessage.
   * @param errorMessage The errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Returns the locale.
   * @return Locale
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Sets the locale.
   * @param locale The locale to set
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}