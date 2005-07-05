package com.tonbeller.wcf.charset;

import java.util.Locale;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * a response wrapper that does not allow the character encoding to be changed.
 * Problem: in servlet 2.3, when {@link ServletResponse#setLocale(java.util.Locale)}
 * is called, the character encoding is changed implicit in an unspecified way.
 * To prevent this, this wrapper does not forward the setLocale call.
 * All JSTL fmt:xxx actions call setLocale on the response, which causes
 * the problem.
 * see JSTL spec, section 8.4
 * @see ServletResponse#setLocale(java.util.Locale)
 * 
 * @author av
 * @since 19.05.2005
 */
class CharsetResponse extends HttpServletResponseWrapper {
  private String contentType;
  private String encoding;
  public CharsetResponse(HttpServletResponse resp, String contentType, String encoding) {
    super(resp);
    this.contentType = contentType;
    this.encoding = encoding;
  }
  
  public void setLocale(Locale arg0) {
    // ignore
  }
  
  public void setContentType(String ct) {
    if (contentType != null && ct != null && ct.startsWith(contentType))
      ct = contentType + ";charset=" + encoding;
    super.setContentType(ct);
  }

}
