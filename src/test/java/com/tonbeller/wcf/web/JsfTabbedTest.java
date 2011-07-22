package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebForm;

/**
 * tests integration of JSF with WCF
 * 
 * @author av
 */
public class JsfTabbedTest extends HttpUnitTestCase {
  
  public JsfTabbedTest(String arg0) {
    super(arg0);
  }

  /** juint complains if this file is empty */
  public void testNothing() {
  }

  
  /**
   * panel1 and panel2 contain an integer input field that is bound
   * to the same java bean property.
   * <p />
   * JSF is no longer supported - so this test is skipped
   */
  public void dont_testJsfForm() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/jsftabbed.faces"));
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    
    // there is no submit button for the curren panel
    SubmitButton button = wf.getSubmitButton("tabbedForm.panel1");
    assertNull(button);
    
    // there is a submit button for the next panel
    button = wf.getSubmitButton("tabbedForm.panel2");
    assertNotNull(button);

    // add invalid int number
    wf.setParameter("tabbedForm.int1", "xxx");
    // try to switch to panel 2
    wf = wf.submit(button).getFormWithID("form01");
    // make sure the panel has not switched because of the error
    button = wf.getSubmitButton("tabbedForm.panel2");
    assertNotNull(button);
    assertEquals("xxx", wf.getParameterValue("tabbedForm.int1"));

    // add valid int number
    wf.setParameter("tabbedForm.int1", "888");
    // successful switch to panel 2
    button = wf.getSubmitButton("tabbedForm.panel2");
    wf = wf.submit(button).getFormWithID("form01");

    button = wf.getSubmitButton("tabbedForm.panel2");
    assertNull(button);

    assertEquals("888", wf.getParameterValue("tabbedForm.int2"));
    
  }

}
