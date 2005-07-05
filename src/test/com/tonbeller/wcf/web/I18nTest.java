package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;

public class I18nTest extends HttpUnitTestCase {

  public I18nTest(String arg0) {
    super(arg0);
  }

  private void check(String name) throws JaxenException, IOException, SAXException,
      TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }

  public void testDe() throws Exception {
    GetMethodWebRequest req = new GetMethodWebRequest(servletUrl + "/i18ndemo.jsp");
    req.setParameter("lang", "de");
    wc.sendRequest(req);
    check("i18n-01");
  }

  public void testEn() throws Exception {
    GetMethodWebRequest req = new GetMethodWebRequest(servletUrl + "/i18ndemo.jsp");
    req.setParameter("lang", "en");
    wc.sendRequest(req);
    check("i18n-02");

  }

}