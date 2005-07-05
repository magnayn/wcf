package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;


public class PageStackTest extends HttpUnitTestCase {

  public PageStackTest(String arg0) {
    super(arg0);
  }

  public void testPage() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/pagestack.jsp"));
    utils.check("pagestack-01", "filter.xsl", "body");
  }
  
  public void testToken() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/pagestack.jsp"));
    // redirect does not work with httpunit 1.5.2?
    WebResponse wr = wc.getCurrentPage();
    WebLink l1 = wr.getFirstMatchingLink(WebLink.MATCH_URL_STRING, "token1.jsp");
    assertNotNull(l1.getParameterValues("token"));
    WebLink l2 = wr.getFirstMatchingLink(WebLink.MATCH_URL_STRING, "token2.jsp");
    assertNotNull(l2.getParameterValues("token"));
    WebLink l3 = wr.getFirstMatchingLink(WebLink.MATCH_URL_STRING, "token3.jsp");
    assertNotNull(l3.getParameterValues("token"));
  }
}
