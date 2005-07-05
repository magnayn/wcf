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
public class Tree2Test extends HttpUnitTestCase {

  public Tree2Test(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testTreeGroups() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/treedemo2.jsp"));
    check("tree2-01");
    // open "F ..."
    utils.submitCell("form01", "tree02", 1, 0, 0);
    // open "F"
    utils.submitCell("form01", "tree02", 2, 0, 0);
    // open "F[1] ..."
    utils.submitCell("form01", "tree02", 4, 0, 0);
    check("tree2-02");
  }
  
}
