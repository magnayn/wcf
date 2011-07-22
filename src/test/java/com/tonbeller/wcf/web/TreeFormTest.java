package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;

public class TreeFormTest extends HttpUnitTestCase {

  public TreeFormTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testTree() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/treeform.jsp"));
    check("treeform-01");
    
    // expand B
    utils.submitCell("form01", "treeform.tree", 1, 0, 0);
    check("treeform-02");
    // expand B[1]
    utils.submitCell("form01", "treeform.tree", 3, 0, 0);
    check("treeform-03");
    // select B[1][2]
    utils.followCell("treeform.tree", 6, 0, 0);
    check("treeform-04");

  }

}
