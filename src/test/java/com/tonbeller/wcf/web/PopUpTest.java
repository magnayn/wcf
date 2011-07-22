package com.tonbeller.wcf.web;

import com.meterware.httpunit.GetMethodWebRequest;
import com.tonbeller.tbutils.httpunit.XmlDiff.DefaultEqualsComparator;


public class PopUpTest extends HttpUnitTestCase {

  public PopUpTest(String arg0) {
    super(arg0);
  }

  public void testPage() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/popup.jsp"));

    // "onmouseover" attribute contains a random id
    utils.getXmlDiff().setAttrComparator(new DefaultEqualsComparator(){
      public boolean equals(Object o1, Object o2) {
        if (String.valueOf(o1).startsWith("cssdropdown"))
          return true;
        return super.equals(o1, o2);
      }});

    utils.check("popup-01", "filter.xsl", "testme");
    utils.followXPath("//blockquote[@id='testme']/div/a", 0);
    utils.check("popup-02", "filter.xsl", "testme");
  }

}
