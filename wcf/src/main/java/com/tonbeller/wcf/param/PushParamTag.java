package com.tonbeller.wcf.param;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

/**
 * Overrides a session param between start and end tag. After the end tag 
 * was processed, the param is restored.
 * 
 * @author av
 * @since 18.02.2005
 */
public class PushParamTag extends SetParamTagBase {
  
  public static final Logger logger = Logger.getLogger(PushParamTag.class);
  
  public int doEndTag() throws JspException {
    SessionParamPool pool = SessionParamPool.instance(pageContext);
    pool.setParam(oldParam);
    oldParam = newParam = null;
    return super.doEndTag();
  }
  
}
