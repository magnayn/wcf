package com.tonbeller.wcf.convert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.format.FormatException;
import com.tonbeller.wcf.ui.TextField;

/**
 * Created on 12.11.2002
 *
 * @author av
 */
public class EditCtrlConverterTest extends ConverterTestBase {

  Document doc;

  /**
   * Constructor for EditCtrlConverterTest.
   * @param arg0
   */
  public EditCtrlConverterTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(EditCtrlConverterTest.class);
  }

  /*
   * bean was not initialized, should not crash
   */
  public void testNull() throws Exception {
    conv.revert(bean, doc);
    Map map = new HashMap();
    conv.validate(map, null, doc, null);
  }

  public void testSetNode() throws Exception {
    bean.setStringValue("hello");
    bean.setIntValue(124);
    Date date = getDate(12, 5, 2000);
    bean.setDateValue(date);

    conv.revert(bean, doc);

    assertEquals("hello", TextField.getValue(getElement(doc, "//textField[@id='stringId']")));
    assertEquals("124", TextField.getValue(getElement(doc, "//textField[@id='intId']")));
    assertEquals("12.05.2000", TextField.getValue(getElement(doc, "//textField[@id='dateId']")));
  }

  public void testSetBean() throws Exception {
    Map map = new HashMap();
    map.put("stringId", new String[] { "hello" });
    map.put("intId", new String[] { "124" });
    map.put("dateId", new String[] { "12.05.2000" });

    conv.validate(map, null, doc, bean);

    assertEquals("hello", bean.getStringValue());
    assertEquals(124, bean.getIntValue());
    assertEquals(getDate(12, 5, 2000), bean.getDateValue());
  }

  public void testError() throws Exception {
    Map map = new HashMap();
    map.put("intId", new String[] { "124x" });
    Element elem = getElement(doc, "//textField[@id='intId']");
    try {
      conv.validate(map, null, doc, bean);
      assertTrue("Exception expected", false);
    } catch (FormatException e) {
    }
    // non empty error message
    assertTrue(elem.getAttribute("error").trim().length() > 0);

    conv.revert(bean, doc);
    assertEquals(0, elem.getAttribute("error").length());

  }

  /*
   * set double property from int field
   */
  public void testConvert() throws Exception {
    Element elm = getElement(doc, "//textField[@id='intId']");
    TextField.setModelReference(elm, "doubleValue");
    Map map = new HashMap();
    map.put("intId", new String[] { "129" });
    conv.validate(map, null, doc, bean);
    assertTrue(Math.abs(bean.getDoubleValue() - 129.0) < 0.001);
  }

  /*
   * password does not have a type attribute!
   */
  public void testPassword() throws Exception {
    Element elem = getElement(doc, "//password");
    TextField.setModelReference(elem, "stringValue");
    Map map = new HashMap();
    map.put("passwordId", new String[] { "secret" });
    conv.validate(map, null, doc, bean);
    assertEquals("secret", bean.getStringValue());

    TextField.setValue(elem, "ops");
    conv.revert(bean, doc);
    assertEquals("secret", TextField.getValue(elem));
  }

  public void testTextArea() throws Exception {
    Element elem = getElement(doc, "//textArea");
    TextField.setModelReference(elem, "stringValue");

    // set value to bean
    Map map = new HashMap();
    map.put("textAreaId", new String[] { "userinput" });
    conv.validate(map, null, doc, bean);
    assertEquals("userinput", bean.getStringValue());

    // bean value to dom
    TextField.setValue(elem, "ops");
    conv.revert(bean, doc);
    assertEquals("userinput", TextField.getValue(elem));

    // no modelReference -> ignored in both directions
    TextField.setModelReference(elem, "");
    TextField.setValue(elem, "ops1");
    bean.setStringValue("ops2");
    conv.revert(bean, doc);
    assertEquals("ops1", TextField.getValue(elem));
    assertEquals("ops2", bean.getStringValue());
    conv.validate(map, null, doc, bean);
    assertEquals("userinput", TextField.getValue(elem));
    assertEquals("ops2", bean.getStringValue());
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    doc = load("EditCtrlConverter.xml");

  }

}
