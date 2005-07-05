package com.tonbeller.wcf.web;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
/**
 * tests virtual grouping of tree nodes
 * @author av
 */
public class Tree3Test extends HttpUnitTestCase {

  public Tree3Test(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  private void click(int row, int but) throws JaxenException, IOException, SAXException {
    utils.submitCell("form01", "tree03", row, 0, but);
  }
  
  void start() throws MalformedURLException, IOException, SAXException {
    GetMethodWebRequest req = new GetMethodWebRequest(servletUrl + "/treedemo3.jsp");
    req.setParameter("init", "true");
    wc.sendRequest(req);
  }
  
  public void testTreeCutPaste1() throws Exception {
    start();
    check("tree3-01");
    // open "A"
    click(0, 0);
    check("tree3-02");
    // swap a0<->a1
    click(1, 1);
    check("tree3-03");
    click(2, 1);
    check("tree3-04");
  }

  public void testTreeCutPaste2() throws Exception {
    start();
    // open a, a1, a0, a00
    click(0, 0);
    click(2, 0);
    click(1, 0);
    click(2, 0);
    check("tree3-11");
    
    // cut a002
    click(5, 1);
    check("tree3-12");
    // paste before a000
    click(3, 1);
    check("tree3-13");

    // cut a001
    click(5, 1);
    check("tree3-14");
    // paste after a004
    click(7, 1);
    check("tree3-15");

    // cut a00
    click(2, 1);
    check("tree3-16");
    // paste after a01
    click(8, 1);
    check("tree3-17");

    // cut a01
    click(2, 1);
    check("tree3-18");
    // collapse a00
    click(3, 0);
    check("tree3-19");
    
  }
  
}
