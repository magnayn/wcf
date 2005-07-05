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

/**
 * number parser, that creates Double objects
 */
public class DoubleHandler extends NumberHandler {

  public Object parse(String s, String userPattern) throws FormatException {
    Number n = (Number) super.parse(s, userPattern);
    return new Double(n.doubleValue());
  }

  public boolean canHandle(Object value) {
    return value instanceof Number;
  }

  public Object toNativeArray(List list) {
    double[] array = new double[list.size()];
    for (int i = 0; i < array.length; i++)
      array[i] = ((Number)list.get(i)).doubleValue();
    return array;
  }

  public Object[] toObjectArray(Object value) {
  	if (value instanceof Double)
  	  return new Double[]{(Double)value};
  	double[] src = (double[])value;
    Double[] dst = new Double[src.length];
    for (int i = 0; i < src.length; i++)
      dst[i] = new Double(src[i]);
    return dst;
  }

}