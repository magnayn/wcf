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
 * basic types that a formatter can handle
 * @author av
 */
public interface BasicTypes {
  public static final String STRING_TYPE  = "string";
  public static final String INT_TYPE     = "int";
  public static final String DATE_TYPE    = "date";
  public static final String BOOLEAN_TYPE = "boolean";
  public static final String DOUBLE_TYPE  = "double";

  /** positive integer > 0 */
  public static final String POSINT_TYPE  = "posint";
  /** required string */
  public static final String REQSTR_TYPE  = "reqstr";
  /** email */
  public static final String EMAIL_TYPE   = "email";

}
