package com.tonbeller.wcf.convert;

import java.util.Locale;

import com.tonbeller.tbutils.xmlunit.XmlTestCase;
import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.format.FormatterFactory;

/**
 * Created on 28.11.2002
 * 
 * @author av
 */
public class ConverterTestBase extends XmlTestCase {
  ConverterTestBean bean;
  Converter conv;

  public ConverterTestBase(String name) {
    super(name);
  }
  
  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    bean = new ConverterTestBean();
    Formatter fmt = FormatterFactory.instance(Locale.GERMANY);
    conv = ConverterFactory.instance(fmt);
  }

}
