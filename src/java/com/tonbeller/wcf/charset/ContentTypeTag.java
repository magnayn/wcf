package com.tonbeller.wcf.charset;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * sets the content type of the response to the configured value
 * 
 * @author av
 * @since 20.05.2005
 */
public class ContentTypeTag extends TagSupport {
  String type;
  private static final Logger logger = Logger.getLogger(ContentTypeTag.class);

  public void setType(String type) {
    this.type = type;
  }

  public int doStartTag() throws JspException {
    if (pageContext.getResponse().isCommitted()) {
      logger.warn("can not set content type - response is committed");
      return EVAL_PAGE;
    }
    String s = type + "; charset=" + CharsetFilter.getEncoding();
    pageContext.getResponse().setContentType(s);
    if (logger.isInfoEnabled())
      logger.info("setting content type to " + s);
    return EVAL_PAGE;
  }
  

}
