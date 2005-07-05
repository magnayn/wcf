package com.tonbeller.wcf.param;

/**
 * indicates that a parameter was expected in the paramPool but was not found.
 * 
 * @author av
 * @since 22.04.2005
 */
public class MissingParameterException extends RuntimeException {

  public MissingParameterException(String message) {
    super(message);
  }

}
