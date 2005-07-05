package com.tonbeller.wcf.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.ui.ListBoxN;
import com.tonbeller.wcf.ui.ListItem;

/**
 * Created on 12.11.2002
 *
 * @author av
 */
public class SelectMultipleConverterTest extends ConverterTestBase {
  Document doc;
  Element lb;
  Element i1;
  Element i2;
  Element i3;
  Map map;

  public void testSelection() throws Exception {
    List selection = new ArrayList();
    selection.add(i2);
    selection.add(i3);
    ListBoxN.setSelectedItems(lb, selection);
    selection = ListBoxN.getSelectedItems(lb);
    assertEquals(2, selection.size());
    assertTrue(selection.get(0) == i2);
    assertTrue(selection.get(1) == i3);
    assertTrue(!ListItem.isSelected(i1));
    assertTrue(ListItem.isSelected(i2));
    assertTrue(ListItem.isSelected(i3));
  }

  public void testUpdateBean() throws ConvertException {
    map.put("lb.valid", new String[] { "x" });
    map.put("lb", new String[] { "i1", "i3" });
    conv.validate(map, null, doc, bean);
    int[] arr = bean.getIntArray();
    assertEquals(2, arr.length);
    assertEquals(1, arr[0]);
    assertEquals(3, arr[1]);
    assertTrue(ListItem.isSelected(i1));
    assertTrue(!ListItem.isSelected(i2));
    assertTrue(ListItem.isSelected(i3));

  }

  public void testUpdateDom() throws ConvertException {
    bean.setIntArray(new int[] { 2 });
    conv.revert(bean, doc);
    List sel = ListBoxN.getSelectedItems(lb);
    assertEquals(1, sel.size());
    Element item = (Element) sel.get(0);
    assertTrue(item == i2);
    assertTrue(!ListItem.isSelected(i1));
    assertTrue(ListItem.isSelected(i2));
    assertTrue(!ListItem.isSelected(i3));
  }

  public void testInvalidItemId() {
    try {
      map.put("lb.valid", new String[] { "x" });
      map.put("lb", new String[] { "invalid-id" });
      conv.validate(map, null, doc, bean);
      assertTrue("Exception expected", false);
    } catch (ConvertException e) {
    }
  }

  public void testInvalidBeanValue() {
    try {
      bean.setIntArray(new int[] { 99 });
      conv.revert(bean, doc);
      assertTrue("Exception expected", false);
    } catch (ConvertException e) {
    }
  }

  /**
   * Constructor for SelectMultipleConverterTest.
   * @param arg0
   */
  public SelectMultipleConverterTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(SelectMultipleConverterTest.class);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    doc = load("ListBoxN.xml");
    lb = getElement(doc, "//listBoxN");
    i1 = getElement(doc, "//listItem[@id='i1']");
    i2 = getElement(doc, "//listItem[@id='i2']");
    i3 = getElement(doc, "//listItem[@id='i3']");
    map = new HashMap();
  }
}
