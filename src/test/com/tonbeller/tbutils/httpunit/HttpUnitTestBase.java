package com.tonbeller.tbutils.httpunit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public abstract class HttpUnitTestBase extends TestCase {
  private static Logger logger = Logger.getLogger(HttpUnitTestBase.class);

  private String servletUrl;
  private String jspName;
  protected WebConversation wc;
  protected HttpUnitUtils utils;

  protected String formID = "form01";
  private boolean checkHtmlErrors;

  public HttpUnitTestBase(String name, String jspName) {
    super(name);
    this.jspName = jspName;
  }

  protected String getJspUrl() {
    return servletUrl + "/" + jspName;
  }

  protected String getJspUrl(String paramName, String paramValue) {
    if (paramName == null)
      paramName = "";
    if (paramValue == null)
      paramValue = "";
    try {
      StringBuffer result = new StringBuffer();
      result.append(servletUrl);
      result.append("/");
      result.append(jspName);
      result.append("?");
      result.append(paramName);
      result.append("=");
      result.append(URLEncoder.encode(paramValue, "ISO-8859-1"));

      /*
       appendProperty(result, "jdbc.driver");
       appendProperty(result, "jdbc.url");
       appendProperty(result, "jdbc.user");
       appendProperty(result, "jdbc.password");
       */

      return result.toString();
    } catch (UnsupportedEncodingException e) {
      logger.error("exception caught", e);
      fail(e.toString());
      return null;
    }
  }

  protected WebResponse submitForm(String buttonName) throws IOException, SAXException {
    WebForm form = getForm();
    SubmitButton sb = form.getSubmitButton(buttonName);
    assertNotNull("submit button not found", sb);
    form.submit(sb);
    return wc.getCurrentPage();
  }

  protected WebForm getForm() throws SAXException {
    return wc.getCurrentPage().getFormWithID(formID);
  }

  protected WebForm getForm(String formId) throws SAXException {
    return wc.getCurrentPage().getFormWithID(formId);
  }

  protected void assertTableSize(String tableID, int rows, int cols) throws SAXException {
    WebTable table = wc.getCurrentPage().getTableWithID(tableID);
    assertEquals("Table RowCount", rows, table.getRowCount());
    assertEquals("Table ColumnCount", cols, table.getColumnCount());
  }

  protected void assertCellValue(String tableID, int row, int col, String value)
      throws SAXException {
    WebTable table = wc.getCurrentPage().getTableWithID(tableID);
    TableCell cell = table.getTableCell(row, col);
    assertEquals("cellValue", value, cell.asText());
  }

  /** 
   * speichert die aktuelle Seite als HTML Datei.
   * Auf der aktuellen seite muss eine Element mit ID = <code>id</code>
   * vorhanden sein, dieses Element wird ueber das XSLT Stylesheet <code>xslName</code>
   * konvertiert und ebenfalls (als XML Datei) gespeichert. Die XML Datei
   * wird mit einer Soll-Version verglichen, bei Unterschieden
   * wird der Test mit Fehler abgebrochen.
   * @param fileName - Name der Datei (ohne Endung .xml bzw .html)
   * @param xslName - Name des Stylesheets, z.B. filter.xsl. Das Stylesheet wird 
   * ueber Class.getResource geladen, dh. muss in diesem Package liegen oder
   * voll qualifiziert sein.
   * @param id - id parameter an das Stylesheet (waehlt z.B. das Element aus, das gespeichert 
   * und geprueft werden soll)
   * @throws IOException
   * @throws TransformerException
   * @throws SAXException
   */
  protected void check(String fileName, String xslName, String id) throws IOException,
      TransformerException, SAXException {
    utils.saveHTML(fileName);
    utils.saveXML(fileName, xslName, id, getCurrentDOM(fileName));
    assertTrue("XML Files \"" + fileName + "\" differ", utils.equalsXML(fileName));
  }

  protected Document getCurrentDOM(String message) throws SAXException {
    HtmlListener hl = HtmlListener.instance();
    try {
      hl.setCheckErrors(checkHtmlErrors);
      Document dom = wc.getCurrentPage().getDOM();
      hl.failOnError(message);
      return dom;
    } finally {
      hl.reset();
    }
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void setUp() throws Exception {
    super.setUp();

    HttpUnitOptions.setScriptingEnabled(false);
    servletUrl = System.getProperty("httpunit.url");
    if (servletUrl == null)
      throw new RuntimeException(
          "missing JVM Parameter httpunit.url, e.g. -Dhttpunit.url=http://localhost:8080/appname");
    wc = new WebConversation();
    wc.setHeaderField("accept-language", getLanguage());
    setAuthorization(wc);

    WebRequest wreq = getStartRequest();
    if (wreq != null)
      wc.sendRequest(wreq);

    utils = new HttpUnitUtils(wc);
  }

  protected void setAuthorization(WebConversation wc) {
    wc.setAuthorization("tdiuser", "tdiuser");
  }

  /**
   * @return the initial request
   */
  protected abstract WebRequest getStartRequest();

  /** liefert Sprache, eg. "en", "en-US", "de", "de-DE" usw */
  protected String getLanguage() {
    return "en-US";
  }

  /**
   * @return String
   */
  public String getJspName() {
    return jspName;
  }

  /**
   * Sets the jspName.
   * @param jspName The jspName to set
   */
  public void setJspName(String jspName) {
    this.jspName = jspName;
  }

  /**
   * @return
   */
  public String getFormID() {
    return formID;
  }

  /**
   * @param string
   */
  public void setFormID(String string) {
    formID = string;
  }

  /**
   * @return
   */
  public String getServletUrl() {
    return servletUrl;
  }

  /**
   * @return
   */
  public WebConversation getWc() {
    return wc;
  }

  /**
   * @param string
   */
  public void setServletUrl(String string) {
    servletUrl = string;
  }

  /**
   * @param conversation
   */
  public void setWc(WebConversation conversation) {
    wc = conversation;
  }

  /**
   * counts the number of nodes in the current page that match xpathExpr
   * 
   * @param msg
   *          error message
   * @param xpathExpr
   *          xpath expression
   * @param nodeCount
   *          expected node count
   */
  public void xpath(String msg, String xpathExpr, int nodeCount) throws SAXException,
      JaxenException {
    Document dom = getCurrentDOM(msg);
    XPath xpath = new DOMXPath(xpathExpr);
    List list = xpath.selectNodes(dom);
    if (list.size() != nodeCount) {
      StringBuffer sb = new StringBuffer();
      sb.append("\n").append(msg).append("\n");
      sb.append(" Node count for expression '").append(xpathExpr).append(" does not match");
      if (list.size() > 0) {
        sb.append("\nFound Nodes: ");
        for (Iterator it = list.iterator(); it.hasNext();) {
          Node node = (Node) it.next();
          sb.append(node.getNodeName());
          if (it.hasNext())
            sb.append(", ");
        }
        sb.append("\n");
      }
      assertEquals(sb.toString(), nodeCount, list.size());
    }
  }

  public int countNodes(String xpathExpr) throws JaxenException, SAXException {
    DOMXPath dx = new DOMXPath(xpathExpr);
    return dx.selectNodes(getCurrentDOM(xpathExpr)).size();
  }

  /**
   * compares node content with a value. Example:
   * <code>&lt;div id='test'&gt;OK&lt;/div&gt;</code> can be cecked via
   * <code>xpath("text should be 'OK'", "//div[@id='test']/text()", "OK");
   * 
   * @param msg Error Message
   * @param xpathExpr XPath to Text Node or Attribute
   * @param nodeValue Value of Text Node or Attribute
   */
  public void xpath(String msg, String xpathExpr, String nodeValue) throws SAXException,
      JaxenException {
    xpath(msg, xpathExpr, 1);
    Document dom = getCurrentDOM(msg);
    XPath xpath = new DOMXPath(xpathExpr);
    Node n = (Node) xpath.selectSingleNode(dom);
    assertEquals(msg, nodeValue, n.getNodeValue());
  }

  public void xpath(String msg, String xpathExpr) throws SAXException, JaxenException {
    xpath(msg, xpathExpr, 1);
  }

  protected WebResponse showPage(String page) throws MalformedURLException, IOException, SAXException {
    WebRequest req = new GetMethodWebRequest(getServletUrl() + "/" + page);
    return wc.sendRequest(req);
  }

  protected WebResponse followLink(String text, int nth) throws JaxenException, IOException, SAXException {
    return utils.followXPath("//a[contains(., '" + text + "')]", nth);
  }

  protected WebResponse followLink(String text) throws JaxenException, IOException, SAXException {
    return followLink(text, 0);
  }

  public boolean isCheckHtmlErrors() {
    return checkHtmlErrors;
  }

  public void setCheckHtmlErrors(boolean checkErros) {
    this.checkHtmlErrors = checkErros;
  }

}
