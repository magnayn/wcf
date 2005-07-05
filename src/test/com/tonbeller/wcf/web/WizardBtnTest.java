package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;

public class WizardBtnTest extends HttpUnitTestCase {

  final String formId = "form01";
  
  public WizardBtnTest(String arg0) {
    super(arg0);
  }

  public void testMultiplePages() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/wizard-btn.jsp"));
    
    // Multiple Page Wizard 
    check("wizardbtn-01a");
    utils.submitButton(formId, "wizformMult01a.next");

    check("wizardbtn-01b");
    utils.submitButton(formId, "wizformMult01b.next");
    
    check("wizardbtn-01c");
    
  }
  
  public void testNoFinish() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/wizard-btn.jsp"));
    
    check("wizardbtn-02a");
    utils.submitButton(formId, "wizformMult02a.next");

    check("wizardbtn-02b");
    utils.submitButton(formId, "wizformMult02b.next");
    
    check("wizardbtn-02c");
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", formId);
  }
  
}
