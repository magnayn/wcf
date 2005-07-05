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

    // ********* test valid input ****************
    wf.setParameter("string", "--ÄÖÜß--");
    wf.setParameter("int", "12");
    wf.setParameter("double", "123.456");
    wf.setParameter("date", "12/31/2003");
    wf.setParameter("password", "xxxx");
    wf.setParameter("textarea", "some\nmulti\nline\ntext\n");
    wf.setParameter("checkbox1", "x");
    wf.setParameter("checkbox2", "x");
    wf.setParameter("list1", "list1.1");
    wf.setParameter("listN", new String[] { "listN.1", "listN.4"});
    wf.setParameter("group1", "radio1");
    wf.submit(wf.getSubmitButtonWithID("formcomp.validate"));
    check("upload-02", "form01");

    // ******** test invalid input *************
    wf = wc.getCurrentPage().getFormWithID("form01");
    wf.setParameter("int", "nan");
    wf.setParameter("double", "nan");
    wf.setParameter("date", "nad");
    wf.submit(wf.getSubmitButtonWithID("formcomp.ok"));
    check("upload-03", "form01");
    wf.submit(wf.getSubmitButtonWithID("formcomp.revert"));
    check("upload-04", "form01");

    // ******** test upload *************
    UploadFileSpec fileSpec = new UploadFileSpec("uploadtest.txt", getClass().getResourceAsStream(
        "uploadtest.txt"), "text/plain");
    wf.setParameter("fileUpload1", new UploadFileSpec[] { fileSpec});
    wf.submit(wf.getSubmitButtonWithID("formcomp.validate"));
    check("upload-05", "uploadInfo");
  }

}