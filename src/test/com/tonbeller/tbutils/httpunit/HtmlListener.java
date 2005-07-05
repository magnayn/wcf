package com.tonbeller.tbutils.httpunit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.meterware.httpunit.parsing.HTMLParserFactory;
import com.meterware.httpunit.parsing.HTMLParserListener;

public class HtmlListener {
  boolean checkWarnings = false;
  boolean checkErrors = false;

  List errors = new ArrayList();
  List warnings = new ArrayList();

  private static final HtmlListener instance = new HtmlListener();

  HTMLParserListener htmlParserListener = new HTMLParserListener() {
    public void warning(URL url, String msg, int line, int column) {
      if (checkWarnings)
        warnings.add(makeMessage(url, msg, line, column));
    }

    public void error(URL url, String msg, int line, int column) {
      if (checkErrors)
        errors.add(makeMessage(url, msg, line, column));
    }

    private String makeMessage(URL url, String msg, int line, int column) {
      return msg + " (" + url + ", line=" + line + ", column=" + column + ")";
    }
  };

  private HtmlListener() {
    HTMLParserFactory.setParserWarningsEnabled(true);
    HTMLParserFactory.addHTMLParserListener(htmlParserListener);
  }

  public static HtmlListener instance() {
    return instance;
  }

  void reset() {
    checkErrors = false;
    errors.clear();
    checkWarnings = false;
    warnings.clear();
  }

  public void failOnError(String context) {
    if (errors.size() != 0) {
      String msg = "HTML Errors in " + context + ": " + errors;
      Assert.fail(msg);
    }
  }

  public boolean isCheckErrors() {
    return checkErrors;
  }
  

  public void setCheckErrors(boolean checkErrors) {
    this.checkErrors = checkErrors;
  }
  

  public boolean isCheckWarnings() {
    return checkWarnings;
  }
  

  public void setCheckWarnings(boolean checkWarnings) {
    this.checkWarnings = checkWarnings;
  }
  

  public List getErrors() {
    return errors;
  }
  

  public List getWarnings() {
    return warnings;
  }

  
  
}
