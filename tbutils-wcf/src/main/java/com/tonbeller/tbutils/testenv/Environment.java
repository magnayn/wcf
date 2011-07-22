package com.tonbeller.tbutils.testenv;

/**
 * If the system properties <code>com.tonbeller.environment</code> is
 * set to <code>test</code> then we are in an test environment and
 * may behave different here and there.
 * 
 * @author av
 */
public class Environment {

  private static boolean test = "test".equals(System.getProperty("com.tonbeller.environment"));

  /**
   * true if this VM runs in a test environment
   * 
   * @return
   */
  public static boolean isTest() {
    return test;
  }
}