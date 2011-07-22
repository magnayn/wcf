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
/*
 * Created on 28.07.2004
 *
 */
package com.tonbeller.wcf.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.utils.SoftException;
import com.tonbeller.wcf.utils.UrlUtils;

/**
 * Ersatz fuer das FacesServlet, dass unter Oracle manchmal in einen illegalen
 * state kommt und an alle clients arrayoutofboundsexception schickt.
 *
 * Fehler trat bei VERSORGE im Oracle 10 Application Server mit Oracle DB auf
 * (mit Tomcat oder MySQL funktioniert es): Daten versorgen, dabei eine
 * Fakt Spalte mit text befuellen, so dass eine Exception entsteht. Browser
 * mit der Exception offen lasssen und versorge nochmal starten. Wenn man das
 * ein paar mal macht, knallt es mit
 * <pre>
 * java.lang.IndexOutOfBoundsException
 *   at javax.faces.component.UIComponentBase$ChildrenList.add(UIComponentBase.java:1448)
 *   at javax.faces.webapp.UIComponentTag.createChild(UIComponentTag.java:1047)
 *   at javax.faces.webapp.UIComponentTag.findComponent(UIComponentTag.java:742)
 *   at javax.faces.webapp.UIComponentTag.doStartTag(UIComponentTag.java:423)
 * </pre>
 * @author av
 */
public class FacesServlet extends HttpServlet {
  final String errorJSP = null;

  private static Logger logger = Logger.getLogger(FacesServlet.class);

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
      IOException {
    String facesUri = req.getServletPath();
    String jspUri = UrlUtils.forceExtension(facesUri, ".faces", ".jsp");

    if (facesUri.equals(jspUri))
      throw new ServletException("faces mapped to jsp");

    if (logger.isInfoEnabled())
      logger.info("FacesServlet: " + facesUri + " -> " + jspUri);
    RequestDispatcher rd = req.getRequestDispatcher(jspUri);
    if (rd == null)
      throw new IllegalArgumentException("could not find RequestDispatcher for " + jspUri);
    try {
      rd.forward(req, res);
    } catch (Exception e) {
      logger.error(null, e);
      if (errorJSP != null) {
        try {
          logger.error("redirecting to error page " + errorJSP, e);
          req.setAttribute("javax.servlet.jsp.jspException", e);
          req.getRequestDispatcher(errorJSP).forward(req, res);
        } catch (Exception e2) {
          // there was an error displaying the error page. We
          // ignore the second error and display the original error
          // instead
          throw new SoftException(e);
        }
      } else
        throw new SoftException(e);
    }
  }

}