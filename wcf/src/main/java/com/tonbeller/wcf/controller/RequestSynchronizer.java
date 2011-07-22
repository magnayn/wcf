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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 *  serializes http requests. Ensures that there is max one request per session
 */
public class RequestSynchronizer {
  private static final String WEBKEY = "requestSynchronizer";
  private static Logger logger = Logger.getLogger(RequestSynchronizer.class);

  /** 
   * the uri that shows the content of the current request. The busy.jsp
   * will redirect to this uri. 
   */
  private String resultURI = null;

  /**
   * the thread that is currently performing a request. In BEA, if you do the a
   * request.getRequestDispatcher().forward() or .include(), this filter will
   * be called recursively in the same thread. We recognize this by comparing the 
   * threads and allowing the current thread to pass recursively.
   * <p/>
   * In Tomcat and Websphere things are different. When doing an include/forward,
   * the filters are not applied recursively, so the recursive requests always get through. 
   */
  private Thread currentThread = null;

  RequestSynchronizer() {
  }

  public static synchronized RequestSynchronizer instance(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    RequestSynchronizer rsync = (RequestSynchronizer) session.getAttribute(WEBKEY);
    if (rsync == null) {
      rsync = new RequestSynchronizer();
      session.setAttribute(WEBKEY, rsync);
    }
    return rsync;
  }

  public interface Handler {

    /**
     * this request that should be processed normally
     */
    void normalRequest() throws Exception;

    /**
     * this is a recursive request like forward, include etc
     */
    void recursiveRequest() throws Exception;

    /**
     * this should redirect to a page saying "your result is computed, please wait ... ".
     * The page could then redirect to RequestSynchronizer.instance(session).getResultURI()
     * to show the result.
     * @param redirect true, when the current page is not the busy page. false, if
     * the current page already is the busy page.
     */
    void showBusyPage(boolean redirect) throws Exception;

    /**
     * returns the URI of the result that the busy page should redirect to
     */
    String getResultURI();

    /**
     * true if this request is the busy page
     */
    boolean isBusyPage();
  }

  private synchronized boolean startNormalRequest(Handler handler) {
    if (currentThread == null) {
      logInfo("normal request");
      // currentThread = Thread.currentThread();
      return true;
    }
    return false;
  }

  private synchronized void endNormalRequest() {
    currentThread = null;
  }

  private synchronized boolean startRecursiveRequest(Handler handler) {
    if (Thread.currentThread().equals(currentThread)) {
      logInfo("recursive request");
      return true;
    }
    return false;
  }

  private synchronized void endRecursiveRequest() {
  }

  public void handleRequest(Handler handler) throws Exception {
    
    // if this is the busy page, just display
    if (handler.isBusyPage()) {
      handler.showBusyPage(false);
      logInfo("handle-busy-false");
      return;
    }

    if (startNormalRequest(handler)) {
      // no other requests active, run this request normally
      try {
        resultURI = handler.getResultURI();
        logInfo("handle-normal");
        handler.normalRequest();
      } finally {
        endNormalRequest();
      }
      resultURI = handler.getResultURI();
      return;
    }

    if (startRecursiveRequest(handler)) {
      // recursive request, same thread
      try {
        logInfo("handle-recursive");
        handler.recursiveRequest();
      } finally {
        endRecursiveRequest();
      }
      return;
    }

    // another thread, redirect to busy page
    logInfo("handle-busy-true");
    handler.showBusyPage(true);
  }

  public String getResultURI() {
    return resultURI;
  }

  private void logInfo(String id) {
    if (logger.isInfoEnabled())
      logger.info("Log " + id + " Thread = " + Thread.currentThread().getName() + ", resultURI = "
          + resultURI + ", currentThread = " + currentThread);
  }

}