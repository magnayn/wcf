/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.utils;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;

/**
 * deletes fiels on session timeout
 * @author av
 * @since Jan 4, 2006
 */
public class SessionTempFileDeleter implements TempFileDeleter, HttpSessionBindingListener {
  List files = new LinkedList();
  private static final String WEBKEY = SessionTempFileDeleter.class.getName();
  private static final Logger logger = Logger.getLogger(SessionTempFileDeleter.class);
  
  private SessionTempFileDeleter () {
  }
  
  public static TempFileDeleter instance(HttpSession session) {
    TempFileDeleter tfd = (TempFileDeleter) session.getAttribute(WEBKEY);
    if (tfd == null) {
      tfd = new SessionTempFileDeleter();
      session.setAttribute(WEBKEY, tfd);
    }
    return tfd;
  }

  public void register(File f) {
    files.add(f);
    if (logger.isInfoEnabled())
      logger.info("registering file for deletion: " + f);
  }

  public void valueBound(HttpSessionBindingEvent arg0) {
  }

  public void valueUnbound(HttpSessionBindingEvent arg0) {
    for (Iterator it = files.iterator(); it.hasNext(); ) {
      File f = (File) it.next();
      try {
        f.delete();
      } catch (Exception e) {
        logger.error(null, e);
      }
    }
  }
}
