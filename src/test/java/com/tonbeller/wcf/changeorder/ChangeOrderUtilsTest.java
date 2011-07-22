package com.tonbeller.wcf.changeorder;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author av
 */
public class ChangeOrderUtilsTest extends TestCase {

  Object[] array;
  ArrayList list;
  
  public ChangeOrderUtilsTest(String arg0) {
    super(arg0);
  }
  
  protected void setUp() {
    array = new Object[] {"A", "B", "C", "D" };
    list = new ArrayList();
    list.addAll(Arrays.asList(array));
  }
  
  void move(int oldIndex, int newIndex) {
    ChangeOrderUtils.move(array, oldIndex, newIndex);
    ChangeOrderUtils.move(list, oldIndex, newIndex);
  }
  
  void assertExpected(String s) {
    char c[] = s.toCharArray();
    assertEquals(c.length, array.length);
    assertEquals(c.length, list.size());
    for (int i = 0; i < c.length; i++) {
      String cs = "" + c[i];
      String is = "" + i;
      assertEquals(is, cs, array[i]);
      assertEquals(is, cs, list.get(i));
    }
  }
  
  void check(int oldIndex, int newIndex, String expected) {
    setUp();
    move(oldIndex, newIndex);
    assertExpected(expected);
  }

  public void testOutOfBounds() {
    check(0, -1, "ABCD");
    check(-1, 0, "ABCD");
    check(-1, -1, "ABCD");
    check(0, 11, "ABCD");
    check(11, 1, "ABCD");
    check(11, -99, "ABCD");
  }
  
  public void checkEquals() {
    check(0,0,"ABCD");
    check(1,1,"ABCD");
    check(2,2,"ABCD");
    check(3,3,"ABCD");
  }
  
  public void testAll() {
    check(0, 0, "ABCD");
    check(0, 1, "BACD");
    check(0, 2, "BCAD");
    check(0, 3, "BCDA");
    
    check(1, 0, "BACD");
    check(1, 1, "ABCD");
    check(1, 2, "ACBD");
    check(1, 3, "ACDB");

    check(2, 0, "CABD");
    check(2, 1, "ACBD");
    check(2, 2, "ABCD");
    check(2, 3, "ABDC");

    check(3, 0, "DABC");
    check(3, 1, "ADBC");
    check(3, 2, "ABDC");
    check(3, 3, "ABCD");
  }

}
