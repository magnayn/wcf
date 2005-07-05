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

/**
 * @author av
 */
public class Pattern {
  String language;
  String pattern;
  String errorMessage;

  /**
   * Returns the language.
   * @return String
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns the pattern.
   * @return String
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Sets the language.
   * @param language The language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Sets the pattern.
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
}