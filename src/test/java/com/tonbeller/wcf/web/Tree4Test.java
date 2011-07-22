package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
/**
 * tests virtual grouping of tree nodes
 * @author av
 */
public class Tree4Test extends HttpUnitTestCase {

  public Tree4Test(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }

  public void testLazySelectionExpansion() throws Exception {
    GetMethodWebRequest gmr = new GetMethodWebRequest(servletUrl + "/treedemo4.jsp");
    gmr.setParameter("init", "true");
    wc.sendRequest(gmr);
    check("tree4-01");

    // refresh B[1]
    utils.submitCell("form01", "tree04", 3, 0, 0);
    check("tree4-02");

    // open B[1][0] ...
    utils.submitCell("form01", "tree04", 4, 0, 0);
    check("tree4-03");
    
  }
  
}
