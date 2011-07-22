package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.UploadFileSpec;
import com.meterware.httpunit.WebForm;

/**
 * Adds file upload to form test. It is important to test all
 * control types since parameters are parsed with a different encoding
 */
public class FormUploadTest extends HttpUnitTestCase {

  public FormUploadTest(String arg0) {
    super(arg0);
  }

  private void check(String name, String id) throws JaxenException, IOException, SAXException,
      TransformerException {
    utils.check(name, "filter.xsl", id);
  }

  /**
   * run form tests w/o faces servlet. This tests WCF EL and navigation
   */
  public void testWcfForm() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/uploaddemo.jsp"));
    runTests();
  }

  private void runTests() throws Exception {
    check("upload-01", "form01");
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    String id = "uploadcomp"; 
    // ********* test valid input ****************
    wf.setParameter(id + ".string", "--ÄÖÜß--");
    wf.setParameter(id + ".int", "12");
    wf.setParameter(id + ".double", "123.456");
    wf.setParameter(id + ".date", "12/31/2003");
    wf.setParameter(id + ".password", "xxxx");
    wf.setParameter(id + ".textarea", "some\nmulti\nline\ntext\n");
    wf.setParameter(id + ".checkbox1", "x");
    wf.setParameter(id + ".checkbox2", "x");
    wf.setParameter(id + ".list1", id + ".list1.1");
    wf.setParameter(id + ".listN", new String[] { id + ".listN.1", id + ".listN.4"});
    wf.setParameter("group1", id + ".radio1");
    wf.submit(wf.getSubmitButtonWithID("uploadcomp.validate"));
    check("upload-02", "form01");

    // ******** test invalid input *************
    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.setParameter(id + ".int", "nan");
    wf.setParameter(id + ".double", "nan");
    wf.setParameter(id + ".date", "nad");
    wf.submit(wf.getSubmitButtonWithID("uploadcomp.ok"));
    check("upload-03", "form01");
    wf.submit(wf.getSubmitButtonWithID("uploadcomp.revert"));
    check("upload-04", "form01");

    // ******** test upload *************
    UploadFileSpec fileSpec = new UploadFileSpec("uploadtest.txt", getClass().getResourceAsStream(
        "uploadtest.txt"), "text/plain");
    wf.setParameter(id + ".fileUpload1", new UploadFileSpec[] { fileSpec});
    wf.submit(wf.getSubmitButtonWithID("uploadcomp.validate"));
    check("upload-05", "uploadInfo");
  }

}