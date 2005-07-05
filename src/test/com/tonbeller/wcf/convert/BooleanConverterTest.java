package com.tonbeller.wcf.convert;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.ui.CheckBox;
import com.tonbeller.wcf.ui.Item;

/**
 * @author av
 */
public class BooleanConverterTest extends ConverterTestBase {
  Document doc;
  Map map;
  Element cb1;
  Element cb2;
  Element rb1;
  Element rb2;

  /**
   * Constructor for CheckBoxConverterTest.
   * @param arg0
   */
  public BooleanConverterTest(String arg0) {
    super(arg0);
  }

  /*
   * Test for void convert(Formatter, Map, Element, Object)
   */
  public void testConvertFormatterMapElementObject() throws Exception {
  	bean.setBooleanValue(false);
  	map.put("cb1", new String[] {"x"});
  	conv.validate(map, null, doc, bean);
  	assertTrue(!bean.isBooleanValue());
  	map.put("cb1.valid", new String[] {"x"});
  	conv.validate(map, null, doc, bean);
  	assertTrue(bean.isBooleanValue());
  	map.remove("cb1");
  	conv.validate(map, null, doc, bean);
  	assertTrue(!bean.isBooleanValue());
  }

  public void testMissingModelReference() throws Exception {
    CheckBox.setModelReference(cb1, "");
    bean.setBooleanValue(false);
    map.put("cb1", new String[] {"x"});
    map.put("cb1.valid", new String[] {"x"});
    conv.validate(map, null, doc, bean);
    assertTrue(Item.isSelected(cb1));
    assertTrue(!bean.isBooleanValue());
  }


  public void testMissingBean() throws Exception {
    Item.setSelected(cb1, false);
    assertTrue(!Item.isSelected(cb1));
    map.put("cb1", new String[] {"x"});
    conv.validate(map, null, doc, null);
    assertTrue(!Item.isSelected(cb1));
    map.put("cb1.valid", new String[] {"x"});
    conv.validate(map, null, doc, null);
    assertTrue(Item.isSelected(cb1));
  }

  /*
   * Test for void convert(Formatter, Object, Element)
   */
  public void testConvertFormatterObjectElement() throws Exception {
  	bean.setBooleanValue(true);
  	conv.revert(bean, doc);
  	assertTrue(Item.isSelected(cb1));
  	bean.setBooleanValue(false);
  	conv.revert(bean, cb1);
  	assertTrue(!Item.isSelected(cb1));
  }



  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    doc = load("BooleanConverterTest.xml");
    map = new HashMap();
    cb1 = getElement(doc, "//checkBox[@id='cb1']");
    cb2 = getElement(doc, "//checkBox[@id='cb2']");
    rb1 = getElement(doc, "//radioButton[@id='rb1']");
    rb2 = getElement(doc, "//radioButton[@id='rb2']");
  }

}
