package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;

public class WizardTest extends HttpUnitTestCase {
  String formId = "form01";
  public WizardTest(String arg0) {
    super(arg0);
  }
  
  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", formId);
  }
  
  public void testStatusLine() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/wizard.jsp"));
    check("wizard-01");
    
    // Fehlermeldung erzeugen
    utils.submitButton(formId, "wizform01.next");
    check("wizard-02");
    
    // AB eintragen und weiter
    utils.getForm(formId).setParameter("stringID", "AB");
    utils.submitButton(formId, "wizform01.next");
    check("wizard-03");
    
    // Fehlermeldung wg ungueltiger Zahl erzeugen
    utils.getForm(formId).setParameter("intID", "xx");
    utils.submitButton(formId, "wizform02.next");
    check("wizard-04");

    // Korrekte Zahl eingeben und weiter
    utils.getForm(formId).setParameter("intID", "123");
    utils.submitButton(formId, "wizform02.next");
    check("wizard-05");
    
    // Fertigstellen
    utils.submitButton(formId, "wizform03.finish");
    Document dom = wc.getCurrentPage().getDOM();
    DOMXPath dx = new DOMXPath("//body[@id='index']");
    assertEquals("index page expected", 1, dx.selectNodes(dom).size());
  }

}
