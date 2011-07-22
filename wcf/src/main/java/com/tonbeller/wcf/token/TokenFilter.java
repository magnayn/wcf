/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.token;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestContextFactoryFinder;
import com.tonbeller.wcf.statusline.StatusLine;

public class TokenFilter implements Filter {
  private String httpParameterName;
  private boolean showMessage;
  private static Random random = new Random();
  private static final Logger logger = Logger.getLogger(TokenFilter.class);

  public void init(FilterConfig config) throws ServletException {
    httpParameterName = config.getInitParameter("token");
    if (httpParameterName == null)
      httpParameterName = "token";
    showMessage = "true".equals(config.getInitParameter("showMessage"));
  }

  private static ThreadLocal threadLocal = new ThreadLocal();

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    // BEA Weblogic calls the filter for imported files too, so in case of recursion
    // we just forward the request.
    if (threadLocal.get() != null) {
      chain.doFilter(req, res);
      return;
    }
    
    try {
      threadLocal.set(Boolean.TRUE); // any object will do

      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;

      String token = request.getParameter(httpParameterName);
      RequestToken s = RequestToken.instance(request.getSession(true));
      s.setHttpParameterName(httpParameterName);
      if (token != null && s.getToken() != null) {
        if (!token.equals(s.getToken())) {
          if (logger.isInfoEnabled())
            logger.info("redirecting to " + s.getPage());
          response.sendRedirect(s.getPage());

          if (showMessage) {
            // create a temporary context that is NOT stored in the ThreadLocal
            RequestContext context = RequestContextFactoryFinder.createContext(request, response,
                false);
            String message = context.getResources(TokenFilter.class).getString(
                "wcf.token.browser.navigation");
            StatusLine.instance(context.getSession()).setMessage(message);
          }
          return;
        }
      }

      synchronized (random) {
        s.setToken(Integer.toHexString(random.nextInt()));
        s.setPage(request.getRequestURI());
      }

      chain.doFilter(request, response);
    } finally {
      threadLocal.set(null);
    }
  }

  public void destroy() {
  }

}
