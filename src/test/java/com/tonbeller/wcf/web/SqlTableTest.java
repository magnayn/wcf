package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;

public class SqlTableTest extends HttpUnitTestCase {

  public SqlTableTest(String arg0) {
    super(arg0);
  }
  
  public void testSessionParam() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/sqltable.jsp"));
    utils.check("sqltable-01", "filter.xsl", "list");
  }

}
