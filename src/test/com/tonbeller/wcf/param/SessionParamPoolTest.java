package com.tonbeller.wcf.param;

import junit.framework.TestCase;

public class SessionParamPoolTest extends TestCase {

  public void testPool() {
    SessionParamPool pool = SessionParamPool.instance();
    
    SessionParam p1 = new SessionParam();
    p1.setName("p1");
    pool.setParam(p1);
    assertEquals(p1, pool.getParam("p1"));
  }
}
