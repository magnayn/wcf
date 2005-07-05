package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;

public class ToolbarTest extends HttpUnitTestCase {

  public ToolbarTest(String arg0) {
    super(arg0);
  }

  private void check(String name)
    throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }

  public void testToolbar() throws Exception {
    GetMethodWebRequest gmw = new GetMethodWebRequest(servletUrl + "/toolbdemo.jsp");
    wc.sendRequest(gmw);
    check("toolb-01");

    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    wf.submit(wf.getSubmitButton("button1"));
    check("toolb-02");

    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.submit(wf.getSubmitButton("button2"));
    check("toolb-03");

    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.submit(wf.getSubmitButton("button3"));
    check("toolb-04");

    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.submit(wf.getSubmitButton("button4"));
    check("toolb-05");

    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.submit(wf.getSubmitButton("button4"));
    check("toolb-06");
  }

}
