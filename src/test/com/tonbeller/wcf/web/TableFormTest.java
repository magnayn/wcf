package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;

public class TableFormTest extends HttpUnitTestCase {

  public TableFormTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testTable() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/tableform.jsp"));
    check("tableform-01");
    
    // sort Date, assert that form input is not lost
    WebForm wf = utils.getForm("form01");
    wf.setParameter("string", "string 2");
    utils.submitCell("form01", "tableform.table", 0, 2, 0);
    check("tableform-02");

    // check all, assert that form input is not lost
    wf = utils.getForm("form01");
    wf.setParameter("string", "string 3");
    utils.submitCell("form01", "tableform.table", 0, 0, 0);
    check("tableform-03");

    // goto last page, assert that form input is not lost
    wf = utils.getForm("form01");
    wf.setParameter("string", "string 4");
    utils.submitCell("form01", "tableform.table", 11, 0, 1);
    check("tableform-04");
    
  }

}
