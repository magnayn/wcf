package com.tonbeller.wcf.utils;

import junit.framework.TestCase;

/**
 */
public class UrlUtilsTest extends TestCase {

  public void testForceExtension() {
    //.setForceExtension(".faces");

    assertEquals("/a/b.faces", UrlUtils.forceExtension("/a/b.jsp", ".faces"));
    assertEquals("/a/b.faces?a=b&c=d", UrlUtils.forceExtension("/a/b.jsp?a=b&c=d", ".faces"));
    assertEquals("/a/b/c", UrlUtils.forceExtension("/a/b/c", ".faces"));
    assertEquals(null, UrlUtils.forceExtension(null, ".faces"));
    assertEquals("", UrlUtils.forceExtension("", ".faces"));
  }

  public void testUrlPatternMatch() {
    assertTrue(UrlUtils.matchPattern("/a/b.jsp", "*.jsp"));
    assertTrue(UrlUtils.matchPattern("b.jsp", "*.jsp"));
    assertFalse(UrlUtils.matchPattern("b.jsq", "*.jsp"));
    assertFalse(UrlUtils.matchPattern("/", "*.jsp"));

    assertTrue(UrlUtils.matchPattern("/", "/"));
    assertTrue(UrlUtils.matchPattern("/a/b.jsp", "/"));

    assertTrue(UrlUtils.matchPattern("/", "/*"));
    assertTrue(UrlUtils.matchPattern("/a/b.jsp", "/*"));

    assertFalse(UrlUtils.matchPattern("/", "/a/b/*"));
    assertFalse(UrlUtils.matchPattern("/a", "/a/b/*"));
    assertFalse(UrlUtils.matchPattern("/a/", "/a/b/*"));
    assertFalse(UrlUtils.matchPattern("/a/c", "/a/b/*"));
    assertFalse(UrlUtils.matchPattern("/a/bc", "/a/b/*"));
    assertFalse(UrlUtils.matchPattern("/a/b.jsp", "/a/b/*"));
    assertTrue(UrlUtils.matchPattern("/a/b", "/a/b/*"));
    assertTrue(UrlUtils.matchPattern("/a/b/", "/a/b/*"));
    assertTrue(UrlUtils.matchPattern("/a/b/c", "/a/b/*"));

    assertFalse(UrlUtils.matchPattern("/", "/a/*"));
    assertTrue(UrlUtils.matchPattern("/a/b.jsp", "/a/*"));
    assertFalse(UrlUtils.matchPattern("/b/a.jsp", "/a/*"));

  }

}
