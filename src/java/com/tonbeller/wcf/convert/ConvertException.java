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
package com.tonbeller.wcf.convert;

/**
 * signals a misconfiguration, e.g. missing / wrong type attribute
 * @author av
 */

public class ConvertException extends RuntimeException {

  /**
   * Constructor for ConvertException.
   */
  public ConvertException() {
    super();
  }

  /**
   * Constructor for ConvertException.
   * @param arg0
   */
  public ConvertException(String arg0) {
    super(arg0);
  }

}
