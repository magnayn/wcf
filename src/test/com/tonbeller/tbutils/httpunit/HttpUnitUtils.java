package com.tonbeller.tbutils.httpunit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElementPredicate;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import com.tonbeller.tbutils.httpunit.FileDiff.FileDiffHandler;

/**
 * Support for httpunit tests. Allows to address
 * submit buttons, checkboxes etc via xpath expressions
 * and table cell coordinates.
 */

public class HttpUnitUtils {

  private DOMXPath xpSubmit;
  private DOMXPath xpAnchor;
  private DOMXPath xpRadio;
  private DOMXPath xpCheckbox;
  private DOMXPath xpSelect;
  private DOMXPath xpOption;
  private DOMXPath xpTextInput;
  private DOMXPath xpTextArea;
  private DOMXPath xpPassword;

  WebConversation wc;
  boolean recordMode;

  XmlDiff xmlDiff;
  FileDiff fileDiff;

  /**
   * Constructor
   * @param wc
   * @throws JaxenException
   */
  public HttpUnitUtils(WebConversation wc) throws JaxenException {
    this.wc = wc;
    xpSubmit = new DOMXPath(".//input[@type='submit'] | .//input[@type='image']");
    xpAnchor = new DOMXPath(".//a[@href]");
    xpCheckbox = new DOMXPath(".//input[@type='checkbox']");
    xpRadio = new DOMXPath(".//input[@type='radio']");
    xpSelect = new DOMXPath(".//select");
    xpOption = new DOMXPath(".//option");
    xpTextInput = new DOMXPath(".//input[@type='text']");
    xpTextArea = new DOMXPath(".//textarea");
    xpPassword = new DOMXPath(".//input[@type='password']");

    recordMode = "true".equals(System.getProperty("httpunit.recordmode"));
    xmlDiff = new XmlDiff(true);
  }

  public Node getCellNode(String tableID, int row, int col) throws SAXException {
    WebTable table = wc.getCurrentPage().getTableWithID(tableID);
    Assert.assertNotNull(table);
    TableCell cell = table.getTableCell(row, col);
    Assert.assertNotNull(cell);
    // XmlUtils.print(cell.getDOM(), new PrintWriter(System.out));
    return cell.getDOM();
  }

  public Element getNth(Node root, DOMXPath xpath, int nth) throws JaxenException, SAXException {
    if(root==null)
      root = wc.getCurrentPage().getDOM();
    
    List list = xpath.selectNodes(root);
    Assert.assertTrue("getNth: " + list.size() + ">" + nth, list.size() > nth);
    return (Element) list.get(nth);
  }

  public WebForm getForm(String formID) throws SAXException {
    WebResponse wr = wc.getCurrentPage();
    if (formID == null)
      return wr.getForms()[0];
    WebForm[] wfs = wr.getForms();
    WebForm wf = wr.getFormWithID(formID);
    Assert.assertNotNull("form " + formID + " not found on page", wf);
    return wf;
  }

  void addParameterValue(String formID, String name, String value) throws SAXException {
    WebForm wf = getForm(formID);
    String[] values = wf.getParameterValues(name);
    if (values == null)
      values = new String[0];
    List list = new ArrayList(Arrays.asList(values));
    if (!list.contains(value)) {
      list.add(value);
      values = (String[]) list.toArray(values);
      wf.setParameter(name, values);
    }
  }

  void removeParameterValue(String formID, String name, String value) throws SAXException {
    WebForm wf = getForm(formID);
    String[] values = wf.getParameterValues(name);
    if (values == null)
      values = new String[0];
    List list = new ArrayList(Arrays.asList(values));
    if (list.contains(value)) {
      list.remove(value);
      if (list.size() == 0)
        wf.removeParameter(name);
      else {
        values = (String[]) list.toArray(values);
        wf.setParameter(name, values);
      }
    }
  }

  /* -------------------------------------- submit buttons ------------------------------------- */

  /**
   * klickt auf den <code>nth</code>-ten  &lt;input type="submit" ... &gt; oder &lt;input type="image" ... &gt; einer Tabellenzelle
   */
  public WebResponse submitCell(String formID, String tableID, int row, int col, int nth)
    throws JaxenException, IOException, SAXException {
    Node root = getCellNode(tableID, row, col);
    return submitXPath(formID, root, xpSubmit, nth);
  }

  /**
   * klickt auf den durch <code>xpath</code> selektierten Submit Button auf der Seite
   */
  public WebResponse submitXPath(String formID, DOMXPath xpath, int nth)
    throws JaxenException, IOException, SAXException {
    return submitXPath(formID, wc.getCurrentPage().getDOM(), xpath, nth);
  }

  /**
   * klickt auf den durch <code>xpath</code> selektierten Submit Button auf der Seite
   */
  public WebResponse submitXPath(String formID, String xpath, int nth)
    throws JaxenException, IOException, SAXException {
    DOMXPath dx = new DOMXPath(xpath);
    return submitXPath(formID, wc.getCurrentPage().getDOM(), dx, nth);
  }
  
  /**
   * klickt auf den durch <code>xpath</code> selektierten Submit Button in root
   */
  public WebResponse submitXPath(String formID, Node root, DOMXPath xpath, int nth)
    throws JaxenException, IOException, SAXException {
    Element button = getNth(root, xpath, nth);
    String name = button.getAttribute("name");
    WebForm wf = getForm(formID);
    SubmitButton sb = wf.getSubmitButton(name);
    return wf.submit(sb);
  }

  public WebResponse submitButton(String formID, String buttonName) throws IOException, SAXException {
    WebForm wf = getForm(formID);
    SubmitButton sb = wf.getSubmitButton(buttonName);
    return wf.submit(sb);
  }

  /**
   * presses the first button in the form whose name attribute contains <code>namePart</code>
   */
  public void submitByNamePart(String formId, String namePart) throws JaxenException, IOException, SAXException {
    submitXPath(formId, "//input[contains(@name,'"+namePart+"')]", 0);
  }

  /**
   * presses the first button in the form whose value attribute contains <code>valuePart</code>
   */
  public void submitByValuePart(String formId, String valuePart) throws JaxenException, IOException, SAXException {
    submitXPath(formId, "//input[contains(@value,'"+valuePart+"')]", 0);
  }
  
  /* -------------------------------------- anchors ------------------------------------- */

  private HTMLElementPredicate matchLink = new HTMLElementPredicate() {
    public boolean matchesCriteria(Object a, Object href) {
      Node node = ((WebLink) a).getDOMSubtree();
      Element e = (Element) node;
      return e.getAttribute("href").equals(href);
    }
  };

  /**
   * folgt dem <code>nth</code> Anchor einer Tabellenzelle
   */
  public WebResponse followCell(String tableID, int row, int col, int nth)
    throws JaxenException, IOException, SAXException {
    return followXPath(getCellNode(tableID, row, col), xpAnchor, nth);
  }

  /**
   * folgt dem <code>nth</code>-ten Anchor auf der Seite, der von <code>xpath</code> selektiert wird.
   */
  public WebResponse followXPath(DOMXPath xpath, int nth)
    throws JaxenException, IOException, SAXException {
    return followXPath(wc.getCurrentPage().getDOM(), xpath, nth);
  }

  /**
   * folgt dem <code>nth</code>-ten Anchor unterhalb von root, der von <code>xpath</code> selektiert wird.
   */
  public WebResponse followXPath(Node root, DOMXPath xpath, int nth)
    throws JaxenException, IOException, SAXException {
    Element anchor = (Element) getNth(root, xpath, nth);
    String href = anchor.getAttribute("href");
    WebResponse wr = wc.getCurrentPage();
    WebLink link = wr.getFirstMatchingLink(matchLink, href);
    return link.click();
  }
  public WebResponse followXPath(String xpath, int i) throws JaxenException, IOException, SAXException {
    return followXPath(new DOMXPath(xpath), i);
  }

  /* -------------------------------------- checkbox / radiobutton ------------------------------------- */

  /**
   * setzt die <code>nth</code>-te checkbox in der durch <code>tableID</code>
   * <code>row</code>, <code>col</code> identifizierten Tabellenzelle. Die Checkbox muss
   * zu dem Formular mit <code>formID</code> gehoeren (d.h. die Tabelle muss in diesem Formular liegen).
   * @param formID id Attribut des HTML form Elements
   * @param tableID id Attribut des HTML table Elements
   * @param row 0 basierter Index der Tabellenzeile
   * @param col 0 basierter Index der Tabellenspalte
   * @param nth 0 basierter Index der Checkboxen innerhalb der Zelle
   * @param checked
   * @throws JaxenException
   * @throws SAXException
   */
  public void setCheckBox(
    String formID,
    String tableID,
    int row,
    int col,
    int nth,
    boolean checked)
    throws JaxenException, SAXException {
    Element cb = (Element) getNth(getCellNode(tableID, row, col), xpCheckbox, nth);
    String name = cb.getAttribute("name");
    String value = cb.getAttribute("value");
    if (checked)
      addParameterValue(formID, name, value);
    else
      this.removeParameterValue(formID, name, value);
  }

  public void assertChecked(
    String formID,
    String tableID,
    int row,
    int col,
    int nth,
    boolean checked)
    throws JaxenException, SAXException {
    Element cb = (Element) getNth(getCellNode(tableID, row, col), xpCheckbox, nth);
    String name = cb.getAttribute("name");
    String value = cb.getAttribute("value");
    WebForm wf = getForm(formID);
    String[] values = wf.getParameterValues(name);
    List valueList = Arrays.asList(values);
    if (checked)
      Assert.assertTrue(valueList.contains(value));
    else
      Assert.assertFalse(valueList.contains(value));
  }

  /**
   * setzt den <code>nth</code>-te radiobutton in der durch <code>tableID</code>
   * <code>row</code>, <code>col</code> identifizierten Tabellenzelle. Der Radio Button muss
   * zu dem Formular mit <code>formID</code> gehoeren (d.h. die Tabelle muss in diesem Formular liegen).
   * dem
   * @param formID id Attribut des HTML form Elements
   * @param tableID id Attribut des HTML table Elements
   * @param row 0 basierter Index der Tabellenzeile
   * @param col 0 basierter Index der Tabellenspalte
   * @param nth 0 basierter Index der Radio Buttons innerhalb der Zelle
   * @throws JaxenException
   * @throws SAXException
   */
  public void setRadioButton(String formID, String tableID, int row, int col, int nth)
    throws JaxenException, SAXException {
    Element cb = (Element) getNth(getCellNode(tableID, row, col), xpRadio, nth);
    String name = cb.getAttribute("name");
    String value = cb.getAttribute("value");
    WebForm wf = getForm(formID);
    // overwrite previous value
    wf.setParameter(name, value);
  }

  /* ----------------------------- select / option --------------------------- */

  /**
   * Selectiert eine Option eines Select Elements.
   * Ermittelt das <code>nth</code>-te &lt;select ...&gt; Eingabefeld in der 
   * der durch <code>tableID</code>, <code>row</code>, <code>col</code> identifizierten Tabellenzelle. 
   * Das Select Element muss zu dem Formular mit <code>formID</code> gehoeren 
   * (d.h. die Tabelle muss in diesem Formular liegen). Selektiert dann die 
   * <code>optindex</code> option.
   * 
   * @param formID id Attribut des HTML form Elements
   * @param tableID id Attribut des HTML table Elements
   * @param row 0 basierter Index der Tabellenzeile
   * @param col 0 basierter Index der Tabellenspalte
   * @param nth 0 basierter Index der select elemente innerhalb der Zelle
   * @param optind 0 basierter index er zu selektierenden Option
   * @throws JaxenException
   * @throws SAXException
   */
  public void setSelect1(String formID, String tableID, int row, int col, int nth, int optind)
    throws JaxenException, SAXException {
    Element select = (Element) getNth(getCellNode(tableID, row, col), xpSelect, nth);
    String name = select.getAttribute("name");
    List options = xpOption.selectNodes(select);
    Element option = (Element) options.get(optind);
    String value = option.getAttribute("value");
    WebForm wf = getForm(formID);
    // overwrite previous value
    wf.setParameter(name, value);
  }

  public void setSelect1(String formID, String tableID, int row, int col, int nth, String optText)
    throws JaxenException, SAXException {
    Element select = (Element) getNth(getCellNode(tableID, row, col), xpSelect, nth);
    String name = select.getAttribute("name");
    List options = xpOption.selectNodes(select);

    for (Iterator it = options.iterator(); it.hasNext();) {
      Element option = (Element) it.next();
      Text text = (Text) option.getChildNodes().item(0);
      if (optText.equals(text.getData())) {
        WebForm wf = getForm(formID);
        // overwrite previous value
        wf.setParameter(name, option.getAttribute("value"));
        return;
      }
    }
    throw new IllegalArgumentException("Option \"" + optText + "\" not found");
  }

  /**
   * Setzt Eingabe in ein Textfeld.
   * Ermittelt das <code>nth</code>-te &lt;input type="text" ...&gt; Eingabefeld in der 
   * der durch <code>tableID</code>, <code>row</code>, <code>col</code> identifizierten Tabellenzelle. 
   * Das input Element muss zu dem Formular mit <code>formID</code> gehoeren 
   * (d.h. die Tabelle muss in diesem Formular liegen). Traegt dann 
   * <code>value</code> dort ein.
   * 
   * @param formID id Attribut des HTML form Elements
   * @param tableID id Attribut des HTML table Elements
   * @param row 0 basierter Index der Tabellenzeile
   * @param col 0 basierter Index der Tabellenspalte
   * @param nth 0 basierter Index der select elemente innerhalb der Zelle
   * @param value der simulierte user input
   * @throws JaxenException
   * @throws SAXException
   */
  public void setTextInput(String formID, String tableID, int row, int col, int nth, String value)
    throws JaxenException, SAXException {
    Element input = (Element) getNth(getCellNode(tableID, row, col), xpTextInput, nth);
    String name = input.getAttribute("name");
    WebForm wf = getForm(formID);
    wf.setParameter(name, value);
  }

  /**
   * Setzt Eingabe in ein Passwortfeld.
   * Ermittelt das <code>nth</code>-te &lt;input type="password" ...&gt; Eingabefeld in der 
   * der durch <code>tableID</code>, <code>row</code>, <code>col</code> identifizierten Tabellenzelle. 
   * Das input Element muss zu dem Formular mit <code>formID</code> gehoeren 
   * (d.h. die Tabelle muss in diesem Formular liegen). Traegt dann 
   * <code>value</code> dort ein.
   * 
   * @param formID id Attribut des HTML form Elements
   * @param tableID id Attribut des HTML table Elements
   * @param row 0 basierter Index der Tabellenzeile
   * @param col 0 basierter Index der Tabellenspalte
   * @param nth 0 basierter Index der select elemente innerhalb der Zelle
   * @param value der simulierte user input
   * @throws JaxenException
   * @throws SAXException
   */
  public void setPassword(String formID, String tableID, int row, int col, int nth, String value)
    throws JaxenException, SAXException {
    Element input = (Element) getNth(getCellNode(tableID, row, col), xpPassword, nth);
    String name = input.getAttribute("name");
    WebForm wf = getForm(formID);
    wf.setParameter(name, value);
  }

  /**
   * Setzt Eingabe in ein TextArea.
   * @see #setTextInput
   */
  public void setTextArea(String formID, String tableID, int row, int col, int nth, String value)
    throws JaxenException, SAXException {
    Element input = (Element) getNth(getCellNode(tableID, row, col), xpTextArea, nth);
    String name = input.getAttribute("name");
    WebForm wf = getForm(formID);
    wf.setParameter(name, value);
  }

  /* ------------------------------------------- logging ------------------------------------------ */

  private File getDir(String name) {
    String path = System.getProperty("httpunit.dir") + name;
    File dir = new File(path);
    dir.mkdirs();
    return dir;
  }

  private void createParentDir(File f) {
    File dir = f.getParentFile();
    dir.mkdirs();
  }

  /**
   * liefert Log-Verzeichnis fuer den aktuelle Test
   */
  public File getLogDir() {
    return getDir("/log");
  }

  /**
   * liefert Verzeichnis mit Referenzdateien fuer Vergleich
   */
  public File getRefDir() {
    return getDir("/ref");
  }

  /**
   * Schreibt die aktuelle Seite als HTML Datei ins Log Verzeichnis
   * @param fileName Dateiname ohne .html Extension
   * @throws IOException
   */
  public void saveHTML(String fileName) throws IOException {
    saveTextFile(fileName + ".html");
  }

  /**
   * Schreibt die aktuelle Seite als Textdatei ins Log Verzeichnis
   * @param fileName Dateiname 
   * @throws IOException
   */
  public void saveTextFile(String fileName) throws IOException {
    File f = new File(getLogDir(), fileName);
    createParentDir(f);
    Writer w = new FileWriter(f);
    w.write(wc.getCurrentPage().getText());
    w.close();
  }
  
  /**
   * Schreibt die aktuelle Seite binär ins Log Verzeichnis
   * @param fileName Dateiname 
   * @throws IOException
   */
  public void saveBinFile(String fileName) throws IOException {
    File f = new File(getLogDir(), fileName);
    createParentDir(f);
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(f);

      InputStream in = null;
      try {
        in = wc.getCurrentPage().getInputStream();
        int c;
        while((c=in.read())>=0)
          out.write(c);
        
      } finally {
        if (in != null)
          in.close();
      }
    } finally {
      if (out != null)
        out.close();
    }
  }

  /**
   * Transformiert die aktuelle Seite mit einem XSL Stylesheet und schreibt
   * das Ergebnis als XML Datei ins Log Verzeichnis. Das XSL dient dazu,
   * aus der Seite die wesentlichen Elemente zu extrahieren.
   *
   * @param fileName Dateiname ohne .xml Extension
   * @param xslName Name des Stylesheets fuer Class.getResource()
   * @param id parameter an das Stylesheet
   * @param root
   * @throws TransformerException
   */
  public void saveXML(String fileName, String xslName, String id, Node root)
    throws TransformerException {
    Transformer trans = getTransformer(xslName);
    trans.setParameter("id", id);
    File f = new File(getLogDir(), fileName + ".xml");
    createParentDir(f);
    trans.transform(new DOMSource(root), new StreamResult(f));
  }

  /**
   * Schreibt Node in eine XML Datei
   */
  public void saveXML(String fileName, Node root) throws TransformerException {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer trans = tf.newTransformer();
    trans.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
    trans.setOutputProperty(OutputKeys.INDENT, "yes");
    trans.setOutputProperty(OutputKeys.METHOD, "xml");
    File f = new File(getLogDir(), fileName + ".xml");
    createParentDir(f);
    trans.transform(new DOMSource(root), new StreamResult(f));
  }
  
  public void check(String fileName, String xslName, String nodeId)
    throws IOException, JaxenException, SAXException, TransformerException {
    saveHTML(fileName);
    saveXML(fileName, xslName, nodeId, wc.getCurrentPage().getDOM());
    Assert.assertTrue(fileName, equalsXML(fileName));
  }

  /**
   * vergleicht 2 Versionen der Datei in LogDir und RefDir.
   * @param fileName
   * @return
   */
  public boolean equalsXML(String fileName) {
    if (isRecordMode())
      return true;
    File f1 = new File(getRefDir(), fileName + ".xml");
    File f2 = new File(getLogDir(), fileName + ".xml");
    return xmlDiff.equals(f1, f2);
  }

  /**
   * vergleicht 2 Versionen der Datei in LogDir und RefDir.
   * @param fileName
   * @return
   */
  public boolean equalsFile(String fileName) throws IOException {
    return equalsFile(fileName, null);
  }

  /**
   * vergleicht 2 Versionen der Datei in LogDir und RefDir.
   * @param fileName
   * @return
   */
  public boolean equalsFile(String fileName, FileDiffHandler handler) throws IOException {
    if (isRecordMode())
      return true;
    File f1 = new File(getRefDir(), fileName);
    File f2 = new File(getLogDir(), fileName);
    FileDiff fileDiff = new FileDiff();
    return fileDiff.equalsFiles(f1, f2, handler);
  }

  private Transformer getTransformer(String name) throws TransformerConfigurationException {
    TransformerFactory tf = TransformerFactory.newInstance();
    URL url = this.getClass().getResource(name);
    Templates templates = tf.newTemplates(new StreamSource(url.toExternalForm()));
    return templates.newTransformer();
  }

  /**
   * true = Dateivergleich liefert immer true
   */
  public boolean isRecordMode() {
    return recordMode;
  }

  /**
   * true = Dateivergleich liefert immer true
   */
  public void setRecordMode(boolean b) {
    recordMode = b;
  }

  public WebConversation setWc(WebConversation wc) {
    WebConversation oldWc = wc;
    this.wc = wc;
    return oldWc;
  }
  
  /**
   * @return den Fragement Identifier der aktuellen URL oder null 
   */
  public String getFragmentIdentifier() {
    String url = wc.getCurrentPage().getURL().toExternalForm();
    int pos = url.indexOf('#');
    if(pos<0)
      return null;
    return url.substring(pos + 1);
  }

  public XmlDiff getXmlDiff() {
    return xmlDiff;
  }
}