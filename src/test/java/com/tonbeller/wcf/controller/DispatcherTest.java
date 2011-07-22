package com.tonbeller.wcf.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 */

public class DispatcherTest extends TestCase {

  DispatcherSupport disp;
  int count;

  class MyListener implements RequestListener {
    public void request(RequestContext context) {
      ++ count;
    }
  }

  public DispatcherTest(String name) {
    super(name);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(DispatcherTest.class);
  }

  public void setUp() {
    disp = new DispatcherSupport();
    count = 0;
  }

  public void testLookup() throws Exception {
    disp.addRequestListener("name1", "value1", new MyListener());
    disp.addRequestListener(null, "value2",    new MyListener());
    disp.addRequestListener("name3", null,  new MyListener());
    disp.addRequestListener(null, null,  new MyListener());
    disp.addRequestListener("name5", null, new MyListener());

    check("name1", "value1", 2);
    check("xxx", "value2", 2);
    check("name3", "xxx", 2);
    check("xxx", "xxx", 1);
    check("name5", "123", 2);
    // image button support: default listener and name5 listener will fire.
    check("name5.x", "123", 2);
  }

  private void check(String name, String value, int size) {
    Map map = new HashMap();
    map.put(name, new String[]{value});
    assertEquals(size, disp.findAll(map).size());
  }
}