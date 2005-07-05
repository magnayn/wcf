package com.tonbeller.wcf.component;

import java.net.URL;

import junit.framework.TestCase;

import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;

import com.tonbeller.wcf.utils.XmlUtils;

public class RendererTagTest extends TestCase {

  public RendererTagTest(String name) {
    super(name);
  }

  public void testSetXmlParameters() throws Exception {
  	URL url = getClass().getResource("paramtest.xml");
  	Document doc = XmlUtils.parse(url);
  	RendererTag rt = new RendererTag();
  	rt.parameters.put("param1", "value1");
  	rt.setXmlParameters( doc);
  	DOMXPath dx = new DOMXPath("/xform/node[@attr1='value1']");
  	assertEquals("param not set", 1, dx.selectNodes(doc).size());
  	
  }

}
