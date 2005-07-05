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
 * creates Integer objects
 * @author av
 */
public class IntegerHandler extends NumberHandler {

  public Object parse(String s, String userPattern) throws FormatException {
    Number n = (Number) super.parse(s, userPattern);
    return new Integer(n.intValue());
  }

  public boolean canHandle(Object value) {
    return value instanceof Integer;
  }
  
  public Object toNativeArray(List list) {
    int[] array = new int[list.size()];
    for (int i = 0; i < array.length; i++)
      array[i] = ((Number)list.get(i)).intValue();
    return array;
  }
  
  public Object[] toObjectArray(Object value) {
  	if (value instanceof Integer)
  	  return new Integer[]{(Integer)value};
  	int[] src = (int[])value;
    Integer[] dst = new Integer[src.length];
    for (int i = 0; i < src.length; i++)
      dst[i] = new Integer(src[i]);
    return dst;
  }
  
}