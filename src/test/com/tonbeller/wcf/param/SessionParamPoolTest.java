package com.tonbeller.wcf.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class SessionParamPoolTest extends TestCase {

  SessionParam makeParam(String name, String value) {
    SessionParam p = new SessionParam();
    p.setName(name);
    p.setSqlValue(value);
    return p;
  }
  
  public void testPool() {
    SessionParamPool pool = SessionParamPool.instance();
    
    SessionParam p1 = makeParam("p1", null);
    pool.setParam(p1);
    assertEquals(p1, pool.getParam("p1"));
  }

  public void testPushPop() throws Exception {
    SessionParamPool pool = SessionParamPool.instance();
    
    SessionParam p1 = makeParam("p1", "p1.value");
    pool.setParam(p1);
    SessionParam p2 = makeParam("p2", "p2.value");
    pool.setParam(p2);
    
    assertEquals(p1, pool.getParam("p1"));
    assertEquals(p2, pool.getParam("p2"));
    assertNull(pool.getParam("p3"));

    List list = new ArrayList();
    SessionParam q2 = makeParam("p2", "p2.new");
    list.add(q2);
    SessionParam q3 = makeParam("p3", "p3.new");
    list.add(q3);

    Map state = pool.pushParams(list);
    
    assertEquals(p1, pool.getParam("p1"));
    assertEquals(q2, pool.getParam("p2"));
    assertEquals(q3, pool.getParam("p3"));

    pool.popParams(state);
    
    assertEquals(p1, pool.getParam("p1"));
    assertEquals(p2, pool.getParam("p2"));
    assertNull(pool.getParam("p3"));
  }
  public void testPushPopDuplicate() throws Exception {
    SessionParamPool pool = SessionParamPool.instance();
    
    SessionParam p1 = makeParam("p1", "p1.value");
    pool.setParam(p1);
    assertEquals(p1, pool.getParam("p1"));
    
    List list = new ArrayList();
    SessionParam q1 = makeParam("p1", "p1.new");
    list.add(q1);
    SessionParam q2 = makeParam("p1", "p1.duplicate");
    list.add(q2);

    Map state = pool.pushParams(list);
    assertEquals(q2, pool.getParam("p1"));
    pool.popParams(state);
    assertEquals(p1, pool.getParam("p1"));
  }
}
