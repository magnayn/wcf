package com.tonbeller.wcf.form;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.tbutils.xmlunit.XmlTestCase;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.TestContext;

/**
 * Created on 28.11.2002
 * 
 * @author av
 */
public class XmlComponentTest extends XmlTestCase {

  RequestContext context;
  Document document;
  XmlComponent component;
  
  TestNodeHandler h0, h1, h2;
  Element e0, e1, e2;
  
  
  public XmlComponentTest(String name) {
    super(name);
  }
  
  public void testGetHandlerGetElement() {
    assertEquals(e0, component.getElement(h0));
    assertEquals(h0, component.getHandler(e0));
    assertEquals(e1, component.getElement(h1));
    assertEquals(h1, component.getHandler(e1));
    assertEquals(e2, component.getElement(h2));
    assertEquals(h2, component.getHandler(e2));
  }


  public void testGetParent() {
    assertEquals(h0, component.getParent(h1));
    assertEquals(h1, component.getParent(h2));
  }

  public void testGetChildren() {
    List c = component.getChildren(h0);
    assertEquals(2, c.size());
    assertEquals(h1, c.get(0));
    assertEquals(h2, c.get(1));

    c = component.getChildren(h1);
    assertEquals(1, c.size());
    assertEquals(h2, c.get(0));

    c = component.getChildren(h2);
    assertEquals(0, c.size());
  }
  
  public void testValidate() {
    assertEquals(0, h0.validateCount);
    assertEquals(0, h1.validateCount);
    assertEquals(0, h2.validateCount);
    component.validate(context);
    assertEquals(1, h0.validateCount);
    assertEquals(1, h1.validateCount);
    assertEquals(1, h2.validateCount);
  }

  public void testRevert() {
    assertEquals(0, h0.revertCount);
    assertEquals(0, h1.revertCount);
    assertEquals(0, h2.revertCount);
    component.revert(context);
    assertEquals(1, h0.revertCount);
    assertEquals(1, h1.revertCount);
    assertEquals(1, h2.revertCount);
  }

  public void testRender() throws Exception {
    assertEquals(0, h0.renderCount);
    assertEquals(0, h1.renderCount);
    assertEquals(0, h2.renderCount);
    component.render(context);
    assertEquals(1, h0.renderCount);
    assertEquals(1, h1.renderCount);
    assertEquals(1, h2.renderCount);
  }
  
  public void setUp() throws Exception {
    context = new TestContext();
    document = load("XmlComponent.xml");
    component = new XmlComponent("id", null, document);
    component.initialize(context);

    e0 = getElement(document, "/node");
    e1 = getElement(document, "/node/node");
    e2 = getElement(document, "/node/node/other/node");
    h0 = (TestNodeHandler)component.getHandler(e0);
    h1 = (TestNodeHandler)component.getHandler(e1);
    h2 = (TestNodeHandler)component.getHandler(e2);
    
  }
}
