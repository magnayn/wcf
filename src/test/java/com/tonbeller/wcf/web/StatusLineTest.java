package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;

public class StatusLineTest extends HttpUnitTestCase {

  public StatusLineTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name, String elemId) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", elemId);
  }
  
  public void testStatusLine() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/statusline.jsp"));
    check("statusline-01", "xmlcontent");
  }

}
