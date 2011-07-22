package com.tonbeller.wcf.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author av
 * @since 07.03.2005
 */
public class IndexOfComparatorTest extends TestCase {
  public void testIndexOfComparator() {
    assertEquals("abcd", toString(asList("abcd")));
    check("abcd", "abcd", "abcd");
    check("dcba", "abcd", "abcd");
    check("xbcy", "abcd", "bcxy");
    check("", "", "");
    check("abc", "", "abc");
    check("", "abc", "");
    check("dcba", "", "dcba");
    check("dcba", "a", "adcb");
    check("dcba", "ab", "abdc");
    check("dcba", "abc", "abcd");
    check("dcba", "abcd", "abcd");
  }

  private void check(String s1, String s2, String s3) {
    List l1 = asList(s1);
    List l2 = asList(s2);
    IndexOfComparator c = new IndexOfComparator(l2);
    Collections.sort(l1, c);
    assertEquals(s3, toString(l1));
  }
  
  List asList(String s) {
    List l = new ArrayList();
    char[] c = s.toCharArray();
    for (int i = 0;i < c.length; i++)
      l.add(new String("" + c[i]));
    return l;
  }
  
  String toString(List l) {
    StringBuffer sb = new StringBuffer();
    for (Iterator it = l.iterator(); it.hasNext();)
      sb.append(it.next());
    return sb.toString();
  }
  
}
