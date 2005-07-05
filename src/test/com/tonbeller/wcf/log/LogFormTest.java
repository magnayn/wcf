package com.tonbeller.wcf.log;

import java.io.File;
import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author av
 */
public class LogFormTest extends TestCase {
  public void testAddConfig() throws Exception {
    String logDirName = System.getProperty("java.io.tmpdir");
    File f = new File(logDirName, "testadd.properties");
    f.delete();
    Locale locale = Locale.GERMANY;
    String context = "test";
    LogHandler lh = new LogHandler(logDirName, locale, context);
    int n = lh.getConfigNames().length;
    lh.addConfig("/com/tonbeller/wcf/log/testadd", "TestAdd");
    assertEquals(n + 1, lh.getConfigNames().length);
    assertEquals("TestAdd", lh.getLabel("testadd"));
  }
}
