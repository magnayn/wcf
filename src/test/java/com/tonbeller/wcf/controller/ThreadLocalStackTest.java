package com.tonbeller.wcf.controller;

import junit.framework.TestCase;

public class ThreadLocalStackTest extends TestCase {
  public void testTLSTack() {
    ThreadLocalStack tls = new ThreadLocalStack();
    assertEmpty(tls);
    tls.push("1");
    tls.push("2");
    assertEquals("2", tls.peek(true));
    assertEquals("2", tls.peek(false));
    tls.pop();
    assertEquals("1", tls.peek(true));
    tls.pop();
    assertEmpty(tls);
  }

  private void assertEmpty(ThreadLocalStack tls) {
    assertNull(tls.peek(false));

    try {
      tls.peek(true);
      fail("EmptyThreadLocalStackException expected");
    } catch (EmptyThreadLocalStackException e) {
      // OK
    }

    try {
      tls.pop();
      fail("EmptyThreadLocalStackException expected");
    } catch (EmptyThreadLocalStackException e) {
      // OK
    }

  }

}
