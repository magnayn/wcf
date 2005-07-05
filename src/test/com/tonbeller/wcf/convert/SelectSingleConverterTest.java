package com.tonbeller.wcf.convert;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.ui.ListBox1;
import com.tonbeller.wcf.ui.ListItem;

public class SelectSingleConverterTest extends ConverterTestBase {
  Document doc;
  Element lb;
  Element i1;
  Element i2;
  Element i3;
  Map map;

  public void testSelection() throws Exception {
  	ListBox1.setSelection(lb, i2);
  	Element selection = ListBox1.getSelectedItem(lb);
  	assertEquals(selection, i2);
    assertTrue(!ListItem.isSelected(i1));
    assertTrue(ListItem.isSelected(i2));
    assertTrue(!ListItem.isSelected(i3));
  }

  public void testUpdateBean() throws ConvertException {
    map.put("lb.valid", new String[] { "x" });
    map.put("lb", new String[] { "i3" });
    conv.validate(map, null, doc, bean);
    int value = bean.getIntValue();
    assertTrue(!ListItem.isSelected(i1));
    assertTrue(!ListItem.isSelected(i2));
    assertTrue(ListItem.isSelected(i3));
    assertEquals(3, value);
  }

  public void testUpdateDom() throws ConvertException {
    bean.setIntValue(2);
    conv.revert(bean, doc);
    Element item = ListBox1.getSelectedItem(lb);
    assertTrue(item == i2);
    assertTrue(!ListItem.isSelected(i1));
    assertTrue(ListItem.isSelected(i2));
    assertTrue(!ListItem.isSelected(i3));
  }


  /**
   * Constructor for SelectMultipleConverterTest.
   * @param arg0
   */
  public SelectSingleConverterTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(SelectSingleConverterTest.class);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    doc = load("ListBox1.xml");
    lb = getElement(doc, "//listBox1");
    i1 = getElement(doc, "//listItem[@id='i1']");
    i2 = getElement(doc, "//listItem[@id='i2']");
    i3 = getElement(doc, "//listItem[@id='i3']");
    map = new HashMap();
  }

}
