package com.tonbeller.wcf.convert;

import java.util.Locale;

import junit.framework.TestCase;

import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.format.FormatterFactory;

/**
 * Created on 05.11.2002
 *
 * @author av
 */
public class ConverterFactoryTest extends TestCase {

  /**
   * Constructor for ConverterFactoryTest.
   * @param arg0
   */
  public ConverterFactoryTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(ConverterFactoryTest.class);
  }

  /*
   * Test for Converter instance()
   */
  public void testInstance() throws Exception {
    Formatter fmt = FormatterFactory.instance(Locale.US);
    Converter cnv = ConverterFactory.instance(fmt);
  }

}
