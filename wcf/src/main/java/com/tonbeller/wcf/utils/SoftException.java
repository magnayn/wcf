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
package com.tonbeller.wcf.utils;

/**
 * wrapper for exceptions
 * 
 * @author av
 */
public class SoftException extends RuntimeException {
  Throwable cause;
  
  public SoftException(Throwable cause) {
    super(cause.toString());
    this.cause = cause;
  }

  public SoftException(String message, Throwable cause) {
    super(message);
    this.cause = cause;
  }
  
  public Throwable getCause() {
    return cause;
  }

}
