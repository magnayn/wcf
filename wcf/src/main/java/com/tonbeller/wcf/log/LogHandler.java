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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tonbeller.tbutils.res.Resources;

public class LogHandler {

  private static Logger logger = Logger.getLogger(LogHandler.class);

  public static final String DEFAULT = "default";
  public static final String NOLOG = "nolog";
  public static final String DEBUG_FILE = "debug-file";
  public static final String DEBUG_STDOUT = "debug-stdout";
  public static final String ERROR_FILE = "error-file";
  public static final String ERROR_STDOUT = "error-stdout";

  private Map configs = new TreeMap();
  private Map labels = new TreeMap();
  private String context;
  private File logFile;
  private File logDir;

  public LogHandler(String logDirName, Locale locale, String context) throws IOException {
    this.context = context;
    this.logDir = initLogDir(logDirName);
    this.logFile = new File(logDir, context + ".log");

    createDefConfigs();
    addConfigs();

  }

  private void createDefConfigs() throws IOException {
    createDefConfig(DEBUG_FILE);
    createDefConfig(DEBUG_STDOUT);
    createDefConfig(ERROR_FILE);
    createDefConfig(ERROR_STDOUT);
    createDefConfig(NOLOG);
  }

  private void createDefConfig(String name) throws IOException {
    String resName = "/com/tonbeller/wcf/log/" + name + ".properties";

    File file = new File(logDir, name + ".properties");
    if (!file.exists())
      copyRes(resName, file);
  }

  private void addConfigs() {
    File files[] = logDir.listFiles(new FilenameFilter() {
      public boolean accept(File f, String name) {
        return name.endsWith(".properties");
      }
    });

    for (int i = 0; i < files.length; i++) {
      String fname = files[i].getName();
      String name = fname.substring(0, fname.lastIndexOf(".properties"));

      configs.put(name, files[i]);
    }
  }

  /**
   * adds a log4j configuration to the list of predefined configs
   * @param resourceName name of the resource, ".properties" will be appended. 
   * Example: "/my/package/log4jdebug"
   * @param label will be presented to the user
   */
  public void addConfig(String resourceName, String label) throws IOException {
    // copy file if not already there
    String name = resourceName;
    int pos = name.lastIndexOf('/');
    if (pos >= 0)
      name = name.substring(pos + 1);
    File file = new File(logDir, name + ".properties");
    if (!file.exists())
      copyRes(resourceName + ".properties", file);
    
    // add to config
    configs.put(name, file);
    labels.put(name, label);
  }

  /**
   * Legt das Logging Verzeichnis im User.Home oder im Temp-Verzeichnis an.
   * Unter Unix darf der Dummy-User im User.Home Verzeichnis (Tomcat) keine
   * Verzeichnisse anlegen
   */
  private File initLogDir(String dir) {
    File baseDir = Resources.instance().getHomeDir();
    File dirObj = logDir(dir, baseDir);
    if (dirObj == null)
      dirObj = logDir(dir, new File("java.io.tmpdir"));
    if (dirObj == null)
      throw new IllegalArgumentException("Not available: " + dirObj);
    return dirObj;
  }

  private File logDir(String dir, File baseDir) {
    File dirObj = new File(dir);
    if (!dirObj.isAbsolute())
      dirObj = new File(baseDir, dir);

    if (dirObj.exists()) {
      if (!dirObj.isDirectory())
        return null;
      return dirObj;
    }

    // Log Directory erzeugen
    if (dirObj.mkdirs())
      return dirObj;
    return null;
  }

  private void copyRes(String resName, File file) throws IOException {
    InputStream is = null;
    try {
      is = getClass().getResourceAsStream(resName);
      if (is == null)
        throw new IllegalArgumentException("Resource " + resName + " not found");
      FileOutputStream os = null;
      try {
        os = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0)
          os.write(buf, 0, len);
      } finally {
        if (os != null)
          os.close();
      }
    } finally {
      if (is != null)
        is.close();
    }
  }

  public String[] getConfigNames() {
    String[] names = new String[configs.keySet().size() + 1];
    names[0] = DEFAULT;

    Iterator it = configs.keySet().iterator();
    int idx = 1;
    while (it.hasNext())
      names[idx++] = (String) it.next();

    return names;
  }
  
  /**
   * returns null or the label of the config name
   * @param configName
   * @return
   */
  public String getLabel(String configName) {
    return (String)labels.get(configName);
  }

  public void applyConfig(String name) throws IOException {

    Properties logProps;

    if (DEFAULT.equals(name))
      logProps = loadDefaultConfig();
    else
      logProps = loadConfig(name);

    LogManager.resetConfiguration();
    PropertyConfigurator.configure(logProps);

    System.out.println("--- Applied new logging configuration ---");
    logger.error("Test error message");
    logger.debug("Test debug message");
  }

  /**
   * Method loadDefaultConfig.
   * @return Properties
   */
  private Properties loadDefaultConfig() throws IOException {
    return loadProperties(getClass().getResource("/log4j.properties"));
  }

  private Properties loadConfig(String name) throws IOException {
    Properties logProps;
    File file = (File) configs.get(name);
    if (file == null)
      throw new IllegalArgumentException("Log configuration " + name + " not found");

    // try to apply the configuration
    logProps = loadProperties(file);

    if (logProps.get("context") == null)
      logProps.put("context", context);
    if (logProps.get("logfile") == null)
      logProps.put("logfile", logFile.getAbsolutePath());
    return logProps;
  }

  private Properties loadProperties(File file) throws IOException {
    Properties props = new Properties();

    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      props.load(in);

      return props;
    } finally {
      if (in != null)
        in.close();
    }
  }

  private Properties loadProperties(URL url) throws IOException {
    Properties props = new Properties();

    InputStream in = null;
    try {
      in = url.openStream();
      props.load(in);

      return props;
    } finally {
      if (in != null)
        in.close();
    }
  }

  public String version() {
    Package p = LogManager.class.getPackage();
    StringBuffer version = new StringBuffer();
    version.append(p.getImplementationVendor());
    version.append(' ');
    version.append(p.getImplementationTitle());
    version.append(' ');
    version.append(p.getImplementationVersion());

    return version.toString();
  }

  /**
   * Returns the logFile.
   * @return File
   */
  public File getLogFile() {
    return logFile;
  }

  /**
   * Sets the logFile.
   * @param logFile The logFile to set
   */
  public void setLogFile(File logFile) {
    this.logFile = logFile;
  }

  /**
   * Method getDefault.
   * @return String
   */
  public static String getDefault() {
    return DEFAULT;
  }

  /**
   * which level is set for the root logger. May be empty 
   */
  public String getRootLoggerLevel() {
    Logger logger = Logger.getRootLogger();

    if (logger.getLevel() != null)
      return logger.getLevel().toString();
    return "";
  }
}
