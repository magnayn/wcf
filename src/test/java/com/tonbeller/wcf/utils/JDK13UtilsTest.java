package com.tonbeller.wcf.utils;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class JDK13UtilsTest extends TestCase {
  public void testEncode() throws UnsupportedEncodingException {
    boolean is13 = System.getProperty("java.vm.version").startsWith("1.3");
    
    String s1 = JDK13Utils.urlEncode("ß", "ISO-8859-1");
    String s2 = JDK13Utils.urlEncode("ß", "UTF-8");
    if (is13)
      assertEquals(s1, s2);
    else
      assertNotSame(s1, s2);
  }
  

  class Ex extends RuntimeException {
    Ex() {
      cause = this;
    }
    Ex(String message) {
      super(message);
      cause = this;
    }
    Ex(String message, Throwable cause) {
      super(message);
      this.cause = cause;
    }
    Throwable cause;
    public Throwable getCause() {
      return cause;
    }
  }
  public void testCause1() {
    Exception c = new Ex("cause");
    Exception w = new Ex("wrapper", c);
    assertEquals(c, JDK13Utils.getCause(w));
  }

  public void testCause2() {
    assertNull(JDK13Utils.getCause(new Ex()));
  }
  
}
