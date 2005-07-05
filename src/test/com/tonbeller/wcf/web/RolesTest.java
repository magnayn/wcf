package com.tonbeller.wcf.web;
import junit.framework.TestCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.tonbeller.tbutils.httpunit.HttpUnitUtils;

public class RolesTest extends TestCase {

  String servletUrl;
  WebConversation wc;
  HttpUnitUtils utils;

  public RolesTest(String arg0) {
    super(arg0);
  }

  void check(String name) throws Exception {
    WebResponse wr = wc.getCurrentPage();
    utils.saveHTML(name);
    utils.saveXML(name, "filter.xsl", "form01", wr.getDOM());
    assertTrue("Files " + name + " differ!", utils.equalsXML(name));
  }

  public void testToolbar() throws Exception {
    wc.setAuthorization("tomcat", "tomcat");
    WebRequest req = new GetMethodWebRequest(servletUrl + "/secure/toolbdemo.jsp");
    wc.sendRequest(req);
    check("roles-01");
  }

  public void testFormfields() throws Exception {
    wc.setAuthorization("tomcat", "tomcat");
    WebRequest req = new GetMethodWebRequest(servletUrl + "/secure/formdemo.jsp");
    wc.sendRequest(req);
    check("roles-02");
  }

  protected void setUp() throws Exception {
    servletUrl = System.getProperty("httpunit.url");
    if (servletUrl == null)
      throw new RuntimeException("missing JVM Parameter httpunit.url, e.g. -Dhttpunit.url=http://localhost:8080/appname");
    wc = new WebConversation();
    wc.setHeaderField("accept-language", "de-DE");
    utils = new HttpUnitUtils(wc);
  }

}
