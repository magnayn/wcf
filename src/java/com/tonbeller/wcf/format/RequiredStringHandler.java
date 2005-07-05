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
 * non empty string
 * 
 * @author av
 */
public class RequiredStringHandler extends StringHandler {
  public Object parse(String object, String userPattern) {
    String s = (String) super.parse(object, userPattern);
    if (s == null || s.trim().length() == 0)
      throw new FormatException(getErrorMessage(""));
    return s;
  }
}
