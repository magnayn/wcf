package com.tonbeller.wcf.pagestack;

import junit.framework.TestCase;

public class PageStackTest extends TestCase {

  public void testPeek() {
    PageStack ps = new PageStack();
    assertNull(ps.peek(0));
    assertNull(ps.peek(1));
    assertNull(ps.peek(43));

    Page a = new Page("a", "A");
    ps.setCurrentPage(a);
    assertEquals(a, ps.peek(0));
    assertNull(ps.peek(1));

    Page b = new Page("b", "B");
    ps.setCurrentPage(b);
    assertEquals(b, ps.peek(0));
    assertEquals(a, ps.peek(1));
    assertNull(ps.peek(2));
    
    Page c = new Page("c", "C");
    ps.setCurrentPage(c);
    assertEquals(c, ps.peek(0));
    assertEquals(b, ps.peek(1));
    assertEquals(a, ps.peek(2));
    assertNull(ps.peek(3));

    ps.setCurrentPage(a);
    assertEquals(a, ps.peek(0));
    assertNull(ps.peek(1));
    
  }

}
