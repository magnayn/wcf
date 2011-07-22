package com.tonbeller.wcf.convert;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author andreas
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.tonbeller.wcf.convert");
    //$JUnit-BEGIN$
    suite.addTest(new TestSuite(BooleanConverterTest.class));
    suite.addTest(new TestSuite(ConverterFactoryTest.class));
    suite.addTest(new TestSuite(EditCtrlConverterTest.class));
    suite.addTest(new TestSuite(SelectMultipleConverterTest.class));
    suite.addTest(new TestSuite(SelectSingleConverterTest.class));
    //$JUnit-END$
    return suite;
  }
}
