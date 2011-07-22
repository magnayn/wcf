package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;

public class CategoryTest extends HttpUnitTestCase {

  public CategoryTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testTree() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/catdemo.jsp"));
    check("cat-01");
    
    // 1.0 -> 2
    utils.submitCell("form01", "catedit01", 1, 0, 0);
    check("cat-02");
    // swap 1.1 / 1.2
    utils.submitCell("form01", "catedit01", 1, 0, 2);
    check("cat-03");
    
  }

}
