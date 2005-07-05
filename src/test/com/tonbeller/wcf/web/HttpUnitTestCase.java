package com.tonbeller.wcf.web;

import junit.framework.TestCase;

import com.meterware.httpunit.WebConversation;
import com.tonbeller.tbutils.httpunit.HttpUnitUtils;

public abstract class HttpUnitTestCase extends TestCase {

  protected HttpUnitUtils utils;
  protected WebConversation wc;
  protected String servletUrl;

  public HttpUnitTestCase(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
    servletUrl = System.getProperty("httpunit.url");
    if (servletUrl == null)
      throw new RuntimeException("missing JVM Parameter httpunit.url, e.g. -Dhttpunit.url=http://localhost:8080/appname");
    wc = new WebConversation();
    wc.setHeaderField("accept-language", "en-US");

    utils = new HttpUnitUtils(wc);
  }

}
