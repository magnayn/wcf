package com.tonbeller.wcf.web;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebForm;

public class TableTest extends HttpUnitTestCase {

  public TableTest(String arg0) {
    super(arg0);
  }

  private void check(String name) throws JaxenException, IOException, SAXException, TransformerException {
    utils.check(name, "filter.xsl", "form01");
  }
  
  public void testTable() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/tabledemo.jsp"));
    check("table-01");
    
    // check Hello World 0
    utils.setCheckBox("form01", "t1", 2, 0, 0, true);
    // sort integer
    utils.submitCell("form01", "t1", 1, 4, 0);
    check("table-02");

    // check Hello World 6
    utils.setCheckBox("form01", "t1", 3, 0, 0, true);
    // sort by date
    utils.submitCell("form01", "t1", 1, 2, 0);
    check("table-03");
    // reverse sort by date
    utils.submitCell("form01", "t1", 1, 2, 0);
    check("table-04");

    // press edit button
    utils.submitCell("form01", "t1", 0, 0, 0);
    check("table-05");
    // remove "hello world" column
    utils.setCheckBox("form01", "t1.form.tree", 0, 0, 0, false);
    // disable paging
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    wf.removeParameter("t1.form.pagable");
    wf.submit(wf.getSubmitButtonWithID("t1.form.ok"));
    check("table-06");

    // press edit button
    utils.submitCell("form01", "t1", 0, 0, 0);
    // swap Image 1 and Image 2
    utils.submitCell("form01", "t1.form.tree", 6, 0, 0);
    wf.submit(wf.getSubmitButtonWithID("t1.form.ok"));
    check("table-07");
  }
  
  public void testPages() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/tabledemo.jsp"));
    // check Hello World 0
    utils.setCheckBox("form01", "t1", 2, 0, 0, true);
    // press edit button
    utils.submitCell("form01", "t1", 0, 0, 0);
    // set pagesize = 4
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    wf.setParameter("t1.form.pagesize", "4");
    wf.submit(wf.getSubmitButtonWithID("t1.form.ok"));
    check("table-11");

    // next page
    utils.submitCell("form01", "t1", 6, 0, 0);
    check("table-12");
    // last page
    utils.submitCell("form01", "t1", 6, 0, 3);
    check("table-13");
    // prev page
    utils.submitCell("form01", "t1", 6, 0, 1);
    check("table-14");
    // first page
    utils.submitCell("form01", "t1", 6, 0, 0);
    check("table-15");
    gotoPage(4, "table-16");
    gotoPage(-99, "table-17");
    gotoPage(99, "table-18");
  }
  
  private void gotoPage(int page, String name) throws Exception {
    WebForm wf = wc.getCurrentPage().getFormWithID("form01");
    wf.setParameter("t1.goto.input", "" + page);
    wf.submit(wf.getSubmitButton("t1.goto.button"));
    check(name);
  }

  public void testUndecoratedTable() throws Exception {
    wc.sendRequest(new GetMethodWebRequest(servletUrl + "/undecorated-table.jsp"));
    check("table-undecorated-01");
  }
}
