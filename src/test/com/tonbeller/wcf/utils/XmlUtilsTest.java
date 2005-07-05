package com.tonbeller.wcf.utils;

import junit.framework.TestCase;

public class XmlUtilsTest extends TestCase {
  public void testEscapeXml() {
    assertEquals("ab&quot;cd", XmlUtils.escapeXml("ab\"cd"));
    // no common escape for both, HTML and XML. 
    assertEquals("ab'cd", XmlUtils.escapeXml("ab'cd"));
    assertEquals("a&amp;b", XmlUtils.escapeXml("a&b"));
    assertEquals("ab&lt;tag&gt;cd", XmlUtils.escapeXml("ab<tag>cd"));
    assertEquals(null, XmlUtils.escapeXml(null));
    assertEquals("", XmlUtils.escapeXml(""));
  }

}
