package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

public class FormTest extends HttpUnitTestCase {

  public FormTest(String arg0) {
    super(arg0);
    HttpUnitOptions.setExceptionsThrownOnScriptError(false);
    HttpUnitOptions.setScriptingEnabled(false);
  }

  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "/com/tonbeller/wcf/web/form.xsl", "form01");
  }

  /**
   * BEA does not recognize the response charEncoding when including files,
   * so we tests &lt;c:import ../> here.
   */
  public void testCharEnc() throws Exception {
    wc.setHeaderField("accept-charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
    GetMethodWebRequest req = new GetMethodWebRequest(servletUrl + "/charencmain.jsp");
    wc.sendRequest(req);
    WebResponse wr = utils.submitButton("form01", "submit");
    check("form-charenc-01");

    // 2-ter test:
    // URL mit UTF-8 encodierten Zeichen, funktioniert im Browser (entgegen der Spec)
    // Funktioniert nicht mit httpunit. httpunit liefert unter Linux andere
    // Ergebnisse als unter Windows, das XML enthaelt non-utf-8 Zeichen.
    WebLink link = wr.getLinkWithID("a02");
    wr = link.click();
    // check("form-charenc-02");
  }

  public void testRequiredField() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/multicolumn.jsp"));
    utils.submitButton("form01", "multicolumnForm.ok");
    check("form-required");
  }

  public void testMultiColumn() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/multicolumn.jsp"));
    check("form-multicolumn");
  }
  /**
   * run form tests w/o faces servlet. This tests WCF EL and navigation
   */
  public void testWcfForm() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/formdemo.jsp"));
    runTests();
  }

  /**
   * run form tests in faces context. This tests non-faces JSP thru faces servlet
   */
  public void testJsfForm() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/formdemo.faces"));
    runTests();
  }

  private void runTests() throws Exception {
    check("form-01");
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    String id = "formcomp"; 

    // ********* test valid input ****************
    wf.setParameter(id + ".string", "--ƒ÷‹ﬂ--");
    wf.setParameter(id + ".int", "12");
    wf.setParameter(id + ".double", "123.456");
    wf.setParameter(id + ".date", "12/31/2003");
    wf.setParameter(id + ".password", "x‰ˆ¸ƒ÷‹ﬂ");
    wf.setParameter(id + ".textarea", "some\nmulti\nline\ntext\n");
    wf.setParameter(id + ".checkbox1", "x");
    wf.setParameter(id + ".checkbox2", "x");
    wf.setParameter(id + ".list1", id + ".list1.1");
    wf.setParameter(id + ".listN", new String[]{ id + ".listN.1", id + ".listN.4"});
    wf.setParameter("group1", id + ".radio1");
    wf.submit(wf.getSubmitButtonWithID("formcomp.validate"));
    check("form-02");

    // ******** test invalid input *************
    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.setParameter(id + ".int", "nan");
    wf.setParameter(id + ".double", "nan");
    wf.setParameter(id + ".date", "nad");
    wf.submit(wf.getSubmitButtonWithID("formcomp.ok"));
    check("form-03");
    wf.submit(wf.getSubmitButtonWithID("formcomp.revert"));
    check("form-04");
  }

}
