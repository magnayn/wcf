package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;

public class TreeTest extends HttpUnitTestCase {

  public TreeTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testTree() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/treedemo.jsp"));
    check("tree-01");
    
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    // move A down
    utils.submitCell("form01", "tree01", 0, 0, 1);
    check("tree-02");
    // open B
    utils.submitCell("form01", "tree01", 0, 0, 0);
    check("tree-03");
    
    // check B[1]
    utils.setCheckBox("form01", "tree01", 2, 0, 0, true);
    // open B[2]
    utils.submitCell("form01", "tree01", 3, 0, 0);
    check("tree-04");

    // check B[2]
    utils.setCheckBox("form01", "tree01", 3, 0, 0, true);
    // move B[2] up
    utils.submitCell("form01", "tree01", 3, 0, 1);
    check("tree-05");

    // close all B's
    utils.submitCell("form01", "tree01", 0, 0, 0);
    // open A
    utils.submitCell("form01", "tree01", 1, 0, 0);
    // delete A[2]
    utils.submitCell("form01", "tree01", 4, 0, 3);
    check("tree-06");

  }

}
