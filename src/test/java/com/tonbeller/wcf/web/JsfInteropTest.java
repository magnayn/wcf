package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

/**
 * tests integration of JSF with WCF
 * 
 * @author av
 */
public class JsfInteropTest extends HttpUnitTestCase {

  public JsfInteropTest(String arg0) {
    super(arg0);
  }

  /** juint complains if this file is empty */
  public void testNothing() {
  }
  
  /**
   * run form tests in faces context. This runs a JSF page
   * that contains a WCF form and a WCF table
   */
  public void dont_testJsfForm() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/jsfgreeting.faces"));

    // add text to jsf form
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.setParameter("form01:userNo", "12345");
    // add text to wcf form
    wf.setParameter("string", "abcdefg");
    // submit form via table: check all table rows
    utils.submitCell("form01", "jsfTableComp", 1, 0, 0);
    
    wf = wc.getCurrentPage().getFormWithID("form01");
    String jsfValue = wf.getParameterValue("form01:userNo");
    assertEquals("12345", jsfValue);
    String wcfValue = wf.getParameterValue("string");
    assertEquals("abcdefg", wcfValue);
    utils.assertChecked("form01", "jsfTableComp", 2, 0, 0, true);

    // remember this form
    wf = wc.getCurrentPage().getFormWithID("form01");

    // forward to non JSF page
    wf.submit(wf.getSubmitButtonWithID("button02"));
    WebResponse wr = wc.getCurrentPage();
    assertEquals("WCF Testpage", wr.getTitle());    

    // forward to JSF page
    wf.submit(wf.getSubmitButtonWithID("button03"));
    wr = wc.getCurrentPage();
    assertEquals("Guess The Number", wr.getTitle());    
  }

}
