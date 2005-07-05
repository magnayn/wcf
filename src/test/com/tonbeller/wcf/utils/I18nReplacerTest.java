package com.tonbeller.wcf.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.w3c.dom.Document;

import com.tonbeller.tbutils.xmlunit.XmlTestCase;

/**
 * @author av
 */
public class I18nReplacerTest extends XmlTestCase {

  public I18nReplacerTest(String name) {
    super(name);
  }
  
  public void testResb() throws Exception {
    ResourceBundle resb = ResourceBundle.getBundle("com.tonbeller.wcf.utils.resources", Locale.GERMAN);
    I18nReplacer r = I18nReplacer.instance(resb);
    runI18nTest(r);
  }

  public void testResources() throws Exception {
    com.tonbeller.tbutils.res.Resources res = com.tonbeller.tbutils.res.Resources.instance(Locale.GERMAN,"com.tonbeller.wcf.utils.resources");
    I18nReplacer r = I18nReplacer.instance(res);
    runI18nTest(r);
  }
  
  private void runI18nTest(I18nReplacer r) throws Exception {
    Document dom = super.load("test.xml");
    r.replaceAll(dom);
    assertNodeCount(dom, "//node[@lang='DE']", 3);
    assertNodeCount(dom, "//other[@lang='DE']", 1);
    assertNodeCount(dom, "//tt[.='DE']", 2);
  }

}
