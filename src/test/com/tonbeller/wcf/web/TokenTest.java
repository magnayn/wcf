package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;

public class TokenTest extends HttpUnitTestCase {

  public TokenTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testToken() throws Exception {
    // record page sequence 1 -> 2 -> 3
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/token1.jsp"));
    check("token-01");
    WebForm wf1 = wc.getCurrentPage().getFormWithID("form01");
    
    wf1.submit(wf1.getSubmitButton("next"));
    check("token-02");
    WebForm wf2 = wc.getCurrentPage().getFormWithID("form01");

    wf2.submit(wf2.getSubmitButton("next"));
    check("token-03");
    WebForm wf3 = wc.getCurrentPage().getFormWithID("form01");
    
    // now resubmit page 1, should lead us back to page 3
    wf1.getRequest().setHeaderField("accept-language", "en-US");
    wf1.submit(wf1.getSubmitButton("next"));
    check("token-04");
  }

}
