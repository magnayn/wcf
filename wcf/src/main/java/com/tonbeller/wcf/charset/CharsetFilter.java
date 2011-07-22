package com.tonbeller.wcf.charset;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tonbeller.tbutils.res.Resources;

/**
 * sets the ServletRequest.setCharacterEncoding to UTF-8.
 * 
 * @author av
 * @since 18.05.2005
 */
public class CharsetFilter implements Filter {

  private static final Logger logger = Logger.getLogger(CharsetFilter.class);
  private static final String ISO88591 = "iso-8859-1";
  private static final String UTF8 = "utf-8";

  private static String encoding = ISO88591;
  private String contentType = null;

  public void init(FilterConfig fc) throws ServletException {
    Resources res = Resources.instance();
    encoding = res.getOptionalString("tbeller.charset", null);
    if (encoding == null)
      encoding = fc.getInitParameter("encoding");
    if (encoding == null)
      encoding = UTF8;
    logger.info("setting encoding to " + encoding);
    
    contentType = fc.getInitParameter("contentType");
  }

  public void destroy() {
    logger.info("setting encoding back to ISO-8859-1");
    encoding = ISO88591;
  }

  /**
   * returns the encoding for the URLEncoder. 
   * @return "utf-8" when the filter was installed in web.xml, "iso8859-1" otherwise.
   */
  public static String getEncoding() {
    return encoding;
  }
  
  /**
   * encodes s into utf-8 if the filter is installed, into iso-8859-1 else
   */
  public static String urlEncode(String s) {
    try {
      return URLEncoder.encode(s, encoding);
    } catch (UnsupportedEncodingException e) {
      logger.error(null, e);
      return s;
    }
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    // if no "content-type" header was sent, we assume utf-8
    if (req.getCharacterEncoding() == null)
      req.setCharacterEncoding(encoding);
    res = new CharsetResponse((HttpServletResponse) res, contentType, encoding);
    chain.doFilter(req, res);
  }

}
