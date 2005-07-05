package com.tonbeller.wcf.web;

import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import com.tonbeller.wcf.log.LogHandler;

public class LogTest extends HttpUnitTestCase {

  public LogTest(String name) {
    super(name);
  }

  public void testForm() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/logdemo.jsp"));

    WebForm wf;
    
    // Logging fuer Errors aktivieren
    wf = wc.getCurrentPage().getFormWithID("logform");
    wf.setParameter("logConfs", String.valueOf(LogHandler.ERROR_STDOUT.hashCode()));
    wf.submit(wf.getSubmitButtonWithID("logdemo.ok"));
    assertLogLevel("ERROR", wc.getCurrentPage());
    
    // Logging fuer alle Meldungen
    wf = wc.getCurrentPage().getFormWithID("logform");
    wf.setParameter("logConfs", String.valueOf(LogHandler.DEBUG_STDOUT.hashCode()));
    wf.submit(wf.getSubmitButtonWithID("logdemo.ok"));
    assertLogLevel("DEBUG", wc.getCurrentPage());
    
    // Logging abschalten
    wf = wc.getCurrentPage().getFormWithID("logform");
    wf.setParameter("logConfs", String.valueOf(LogHandler.NOLOG.hashCode()));
    wf.submit(wf.getSubmitButtonWithID("logdemo.ok"));
    assertLogLevel("OFF", wc.getCurrentPage());
    
  }
  /**
   * Method assertLogLevel.
   * @param ref
   * @param webResponse
   */
  private void assertLogLevel(String ref, WebResponse webResponse) {
    try {
      WebTable tab = webResponse.getTables()[0];
      assertEquals(ref, tab.getCellAsText(1,1));
    }
    catch (SAXException e) {
      e.printStackTrace();
      fail(e.toString());
    }
  }

}
