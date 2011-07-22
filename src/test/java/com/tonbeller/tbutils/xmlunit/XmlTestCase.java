package com.tonbeller.tbutils.xmlunit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import junit.framework.TestCase;

import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * xml test utilities
 *
 * @author av
 */
public abstract class XmlTestCase extends TestCase {

  public XmlTestCase(String name) {
    super(name);
  }

  /**
   * loads a document from xml file
   */
  public Document load(String name)
    throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(false);
    dbf.setExpandEntityReferences(true);

    DocumentBuilder parser = dbf.newDocumentBuilder();
    InputStream is = getClass().getResourceAsStream(name);
    return parser.parse(is);
  }
  
  public Document transform(String name, Document src) throws TransformerFactoryConfigurationError, TransformerException {
    Transformer tf = TransformerFactory.newInstance().newTransformer();
    DOMSource domsrc = new DOMSource(src);
    DOMResult domres = new DOMResult();
    tf.transform(domsrc, domres);
    return (Document)domres.getNode();
  }

  /**
   * evaluates xpathExpr on node and asserts that the
   * result contains <code>nodeCount</code> nodes
   */
  public void assertNodeCount(Node node, String xpathExpr, int nodeCount) throws JaxenException {
    XPath xpath = new DOMXPath(xpathExpr);
    List list = xpath.selectNodes(node);
    assertEquals("Node count" + xpathExpr, nodeCount, list.size());
  }

  /**
   * returns the <code>Element</code> that <code>xpathExpr</code> identifies
   */
  public Element getElement(Node node, String xpathExpr) throws JaxenException {
    XPath xpath = new DOMXPath(xpathExpr);
    List list = xpath.selectNodes(node);
    assertEquals("Node count" + xpathExpr, 1, list.size());
    return (Element) list.get(0);
  }

  /**
   * creates a date
   */
  public Date getDate(int day, int month, int year) {
    Calendar cal = GregorianCalendar.getInstance(Locale.GERMANY);
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

}
