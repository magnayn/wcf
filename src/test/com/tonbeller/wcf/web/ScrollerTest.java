package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;
import com.tonbeller.wcf.scroller.Scroller;

public class ScrollerTest extends HttpUnitTestCase {

  public ScrollerTest(String arg0) {
    super(arg0);
  }

  public void testScroller() throws Exception {
    final int xPos = 4711;
    final int yPos = 815;

    // Simulate browser navigation
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/scroller.jsp?wcfXCoord=" + xPos
        + "&wcfYCoord=" + yPos + "&" + Scroller.FORCE_SCROLLER + "=true"));
    utils.check("scroller-01", "/com/tonbeller/wcf/web/scroller.xsl", "form01");
  }
}