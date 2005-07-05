package com.tonbeller.wcf.charset;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class CharsetTag extends TagSupport {
  private static final Logger logger = Logger.getLogger(CharsetTag.class);
  public int doStartTag() throws JspException {
    String enc = pageContext.getResponse().getCharacterEncoding();
    try {
      pageContext.getOut().print(enc);
    } catch (IOException e) {
      logger.error(null, e);
    }
    return EVAL_PAGE;
  }
}
