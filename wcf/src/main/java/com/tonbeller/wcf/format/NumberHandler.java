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

import java.text.DecimalFormat;
import java.text.ParsePosition;


/**
 * parses/prints numbers via DecimalFormat
 */
public abstract class NumberHandler extends FormatHandlerSupport {
  double minValue = Double.NaN;
  
  public String format(Object o, String userPattern) {
    if (o == null) {
      return "";
    }

    DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(getLocale());
    df.applyPattern(findPattern(userPattern));

    return df.format(o);
  }


  public Object parse(String s, String userPattern) throws FormatException {
    if (s == null) {
      throw new FormatException(getErrorMessage(""));
    }

    s = s.trim();

    if (s.length() == 0) {
      throw new FormatException(getErrorMessage(""));
    }

    DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(getLocale());
    df.applyPattern(findPattern(userPattern));

    ParsePosition pos = new ParsePosition(0);
    Number n = (Number) df.parse(s, pos);

    if ((n == null) || (pos.getIndex() != s.length()))
      throw new FormatException(getErrorMessage(s));
    
    if (!Double.isNaN(minValue) && n.doubleValue() < minValue)
      throw new FormatException(getErrorMessage(s));

    return n;
  }

  /**
   * Returns the minValue.
   * @return double
   */
  public double getMinValue() {
    return minValue;
  }

  /**
   * Sets the minValue.
   * @param minValue The minValue to set
   */
  public void setMinValue(double minValue) {
    this.minValue = minValue;
  }

}