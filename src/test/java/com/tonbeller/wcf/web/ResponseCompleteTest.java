package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebResponse;

public class ResponseCompleteTest extends HttpUnitTestCase {

  public ResponseCompleteTest(String arg0) {
    super(arg0);
  }

  public void testResponseComplete() throws Exception {
    GetMethodWebRequest req = new GetMethodWebRequest(servletUrl + "/responsecomplete.jsp");
    wc.sendRequest(req);
    req.setParameter("complete", "x");
    WebResponse wr = wc.sendRequest(req);
    // jsp writes one blank to output stream
    assertEquals(" ", wr.getText());
  }


}
