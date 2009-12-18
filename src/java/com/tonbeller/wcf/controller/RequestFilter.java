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
package com.tonbeller.wcf.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.tonbeller.tbutils.testenv.Environment;
import com.tonbeller.wcf.utils.DomUtils;
import com.tonbeller.wcf.utils.JDK13Utils;
import com.tonbeller.wcf.utils.UrlUtils;

/**
 * Informs <code>RequestListeners</code> of incoming request. If a nextView attribute is present
 * in the current request, it will forward to that. Otherwise the filter chain is executed.
 */

public class RequestFilter implements Filter {

  private static Logger logger = Logger.getLogger(RequestFilter.class);

  static final String NEXTVIEW = RequestFilter.class.getName() + ".nextview";
  static final String ISNEW = RequestFilter.class.getName() + ".isnew";

  /** Name of the Request Attribute containing the Request.getRequestURI */
  public static final String CONTEXT = "context";

  /** if the session attribute FORCE_INDEX_JSP exists, then the client will be redirected to index.jsp */
  public static final String FORCE_INDEX_JSP = "com.tonbeller.wcf.controller.FORCE_INDEX_JSP";

  /**
   * If this request parameter is present, the DomUtils.randomId()
   * will be reset once.
   */
  public static final String RESET_RANDOM_SEED = "resetRandomSeed";

  private String errorJSP = null;
  private String busyJSP = null;
  private String indexJSP = null;
  private String[] passThru = null;

  private String forceExtension = null;

  public RequestFilter() {
  }

  public void init(FilterConfig config) throws ServletException {
    errorJSP = config.getInitParameter("errorJSP");
    forceExtension = config.getInitParameter("forceExtension");
    indexJSP = config.getInitParameter("indexJSP");
    busyJSP = config.getInitParameter("busyJSP");

    String patternList = config.getInitParameter("passThru");
    passThru = UrlUtils.parseUrlPatternList(patternList);
  }

  class MyHandler implements RequestSynchronizer.Handler {
    protected RequestContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected FilterChain filterChain;

    public MyHandler(RequestContext context, FilterChain filterChain) {
      this.context = context;
      this.request = context.getRequest();
      this.response = context.getResponse();
      this.filterChain = filterChain;
    }

    public String getResultURI() {
      // did the controller change the view?
      String uri = (String) request.getAttribute(NEXTVIEW);
      if (uri != null) {
        uri = UrlUtils.forceExtension(uri, forceExtension);
        uri = UrlUtils.redirectURI(request, uri);
        return uri;
      }

      // no, redisplay the current request
      return request.getRequestURI();
    }

    public void normalRequest() throws Exception {
      HttpSession session = request.getSession(true);
      try {

        if (request.getParameter(RESET_RANDOM_SEED) != null)
          DomUtils.setRandomSeed(123);

        if (redirectToIndex())
          return;

        // fire events
        Controller controller = WcfController.instance(session);
        controller.request(context);

        // someone has called sendRedirect() on the response?
        if (response.containsHeader("Location"))
          return;
        // someone has called sendError() or sendRedirect() on the response?
        if (context.isResponseComplete())
          return;

        // The current page is redisplayed unless there was an error
        // in which case we go to the start page.
        String uri = (String) request.getAttribute(NEXTVIEW);
        if (session.getAttribute(FORCE_INDEX_JSP) != null) {
          session.removeAttribute(FORCE_INDEX_JSP);
          uri = indexJSP;
        }

        if (uri != null)
          forward(uri);
        else
          filterChain.doFilter(request, response);
      } catch (Exception e) {
        handleException(e);
      }
    }

    public void recursiveRequest() throws Exception {
      try {
        filterChain.doFilter(request, response);
      } catch (Exception e) {
        handleException(e);
      }
    }
    
    private void handleException(Exception e) throws Exception {
      logger.error("exeption", e);
      logger.error("cause", JDK13Utils.getCause(e));
      
      // avoid endless recursion in BEA (exception in error page)
      if (isErrorPage())
        throw e;
      
      if (errorJSP != null) {
        try {
          logger.info("redirecting to error page " + errorJSP);
          request.setAttribute("javax.servlet.jsp.jspException", e);
          request.getRequestDispatcher(errorJSP).forward(request, response);
        } catch (Exception e2) {
          // there was an error displaying the error page. We
          // ignore the second error and display the original error
          // instead
          throw e;
        }
      } else
        throw e;
    }

    public void showBusyPage(boolean redirect) throws Exception {
      if (redirectToIndex())
        return;
      if (busyJSP != null) {
        if (redirect)
          forward(busyJSP);
        else
          filterChain.doFilter(request, response);
      } else
        throw new IllegalStateException("concurrent requests and no busy.jsp defined in web.xml");
    }

    public boolean isBusyPage() {
      if (busyJSP == null)
        return false;
      return request.getRequestURI().endsWith(busyJSP);
    }
    
    public boolean isErrorPage() {
      if (errorJSP == null)
        return false;
      return request.getRequestURI().endsWith(errorJSP);
    }

    /**
     * @param uri
     * @throws IOException
     */
    private void forward(String uri) throws IOException {
      uri = UrlUtils.redirectURI(request, uri);
      uri = UrlUtils.forceExtension(uri, forceExtension);
      if (logger.isInfoEnabled())
        logger.info("redirecting to " + uri);
      response.sendRedirect(uri);
    }

    /**
     * true, if the current request was redirected to the index page
     * because there is no valid session.
     */
    protected boolean redirectToIndex() throws Exception {
      if (indexJSP != null) {
        // do not redirect to index.jsp while testing
        if (Environment.isTest())
          return false;

        HttpSession session = context.getSession();
        boolean isNew = session.isNew();
        if (!isNew)
          isNew = !"false".equals(session.getAttribute(ISNEW));
        if (isNew) {
          session.setAttribute(ISNEW, "false");
          forward(indexJSP);
          return true;
        }
      }
      return false;
    }

  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    MultiPartEnabledRequest request = new MultiPartEnabledRequest((HttpServletRequest) req);
    HttpServletResponse response = (HttpServletResponse) res;

    HttpSession session = request.getSession(true);
    MDC.put("SessionID", session.getId());

    String cpath = request.getContextPath();
    request.setAttribute(CONTEXT, cpath);

    RequestContext context = RequestContextFactoryFinder.createContext(request, response, true);
    try {
      // set locale for JSTL tags
      Config.set(request, Config.FMT_LOCALE, context.getLocale());
      // log if necessary
      if (logger.isInfoEnabled())
        logRequest(request);

      if (passThru(req)) {
        chain.doFilter(req, res);
        return;
      }

      MyHandler handler = new MyHandler(context, chain);
      long t1 = System.currentTimeMillis();
      // RequestSynchronizer.instance(request).handleRequest(handler);
      handler.normalRequest();
      long t2 = System.currentTimeMillis();
      if (logger.isInfoEnabled())
        logger.info("Request Execution total time: " + (t2 - t1) + " ms");
    } catch (Throwable e) {
      PrintWriter out = null;
      try {
        out = response.getWriter();
      } catch (Exception e2) {
        out = new PrintWriter(System.out);
        logger.error("No output writer could be retrieved, logging to stdout");
      }
      out.println("<html><body>");
      while (e != null) {
        logger.error("Error handling request", e);
        out.println();
        out.println("<h2>" + e.toString() + "</h2><pre>");
        e.printStackTrace(out);
        out.println("</pre>");

        Throwable prev = e;
        e = JDK13Utils.getCause(e);
        if (e == prev)
          break;
      }
      out.println("</body></html>");
    } finally {
      context.invalidate();
    }
  }

  private boolean passThru(ServletRequest req) {
    if (passThru == null)
      return false;
    HttpServletRequest hsr = (HttpServletRequest) req;
    return UrlUtils.matchPattern(hsr, passThru);
  }

  /** for testing */
  void setForceExtension(String forceExtension) {
    this.forceExtension = forceExtension;
  }

  private void logRequest(HttpServletRequest request) {
    logger.info(">>> Request " + request.getScheme() + "://" + request.getServerName() + ":"
        + request.getServerPort() + request.getContextPath() + request.getServletPath() + "["
        + request.getPathInfo() + "]" + "[?" + request.getQueryString() + "]");
  }

  public static void setSessionIsNew(HttpSession session, boolean isNew) {
    session.setAttribute(ISNEW, Boolean.toString(isNew));
  }

  public static void setForceIndexJsp(HttpSession session, boolean b) {
    if (b)
      session.setAttribute(FORCE_INDEX_JSP, "true");
    else
      session.removeAttribute(FORCE_INDEX_JSP);
  }

  public void destroy() {
  }

}