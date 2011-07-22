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
package com.tonbeller.wcf.log;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.w3c.dom.Document;

import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.form.FormComponent;
import com.tonbeller.wcf.format.FormatException;
import com.tonbeller.wcf.utils.SoftException;

/**
 * Logging administration form
 */
public class LogForm extends FormComponent {

  LogHandler logHandler;
  String logDir;

  // Properties
  String logConf = LogHandler.getDefault();
  String logVersion;

  /**
   * Constructor
   * @param id
   * @param doc
   * @param logHdl
   * @throws IOException
   */
  public LogForm(String id, Component parent, Document doc, String logDir) throws Exception {
    super(id, parent, doc);
    
    this.logDir = logDir;
  }

  /**
   * @see com.tonbeller.wcf.component.Component#initialize(com.tonbeller.wcf.controller.RequestContext)
   */
  public void initialize(RequestContext context) throws Exception {
    try {
      String ctxname = context.getRequest().getContextPath();
      if(ctxname.startsWith("/"))
        ctxname = ctxname.substring(1);
      
      this.logHandler = createLogHandler(logDir, context.getLocale(), ctxname);
      this.logVersion = logHandler.version();
    } catch(IOException e) {
      throw new SoftException(e);
    }

    // jetzt erst die NodeHandler intialisieren
    super.initialize(context);
  }
  
  /**
   * Method createLogHandler.
   * @param context
   * @return LogHandler
   */
  protected LogHandler createLogHandler(String logDir, Locale locale, String ctxname) throws IOException {
    return new LogHandler(logDir, locale, ctxname);
  }
  
  /**
   * Method getLogHandler.
   * @return LogHandler
   */
  public LogHandler getLogHandler() {
    return logHandler;
  }

  /**
   * Returns the logConf.
   * @return String
   */
  public String getLogConf() {
    return logConf;
  }

  /**
   * Sets the logConf.
   * @param logConf The logConf to set
   */
  public void setLogConf(String logConf) {
    this.logConf = logConf;
    try {
      logHandler.applyConfig(logConf);
    } catch(Exception e) {
      String msg = e.getMessage();
      if(msg == null)
        msg = e.toString();
      throw new FormatException(msg);
    }
  }

  /**
   * Returns the logVersion.
   * @return String
   */
  public String getLogVersion() {
    return logVersion;
  }

  /**
   * Returns the logFile.
   * @return String
   */
  public String getLogFile() {
    return logHandler.getLogFile().getAbsolutePath();
  }

  /**
   * Sets the logFile.
   * @param logFile The logFile to set
   */
  public void setLogFile(String logFile) {
    logHandler.setLogFile(new File(logFile));
  }

  /**
   * Returns the logLevel.
   * @return String
   */
  public String getLogLevel() {
    return logHandler.getRootLoggerLevel();
  }

}
