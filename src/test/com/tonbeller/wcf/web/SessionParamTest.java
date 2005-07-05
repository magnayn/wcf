package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;

public class SessionParamTest extends HttpUnitTestCase {

  public SessionParamTest(String arg0) {
    super(arg0);
  }
  
  public void testSessionParam() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/paramtest.jsp"));
    utils.check("paramtest-01", "filter.xsl", "test01");
    utils.followXPath("//a[contains(., 'AAA')]", 0);
    utils.check("paramtest-02", "filter.xsl", "test01");
  }
}
