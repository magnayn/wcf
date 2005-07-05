package com.tonbeller.wcf.format;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.TestCase;


/**
 * @author av
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FormatterTest extends TestCase {

  Formatter en;
  Formatter de;

  /**
   * Constructor for FormatterTest.
   * @param arg0
   */
  public FormatterTest(String arg0) {
    super(arg0);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    en = FormatterFactory.instance(Locale.ENGLISH);
    de = FormatterFactory.instance(Locale.GERMAN);
  }
  
  public void testString() {
    assertEquals("123", de.parse("string", "123", null));
    assertEquals("123", de.format("string", "123", null));
    assertEquals("", de.format("string", null, null));
  }
  
  public void testInteger() {
    assertEquals(new Integer(123), de.parse("int", "123", null));
    try {
      en.parse("int", "1x23", null);
      assertEquals("should throw exception", true, false);
    }
    catch (FormatException e) {
    }
  }
  

  public void testDouble() {
    FormatHandler h = en.getHandler("double");
    assertEquals(new Double(123.45), h.parse("123.45", null));
    h = de.getHandler("double");
    assertEquals(new Double(123.45), h.parse("123,45", null));
    assertEquals("123,45", h.format(new Double(123.45), null));
  }  
  
  
  public void testDate() {
    GregorianCalendar gc = new GregorianCalendar(2000, Calendar.AUGUST, 12);
    assertEquals("08/12/2000", en.format("date", gc.getTime(), null));
  }
  

  public void testRegex() {
    assertEquals("abc@de.com", de.parse("email", "abc@de.com", null));
    try {
      en.parse("email", "abcde.com", null);
      assertEquals("should throw exception", "abcde.com", null);
    }
    catch (FormatException e) {
    }
  }
  

}
