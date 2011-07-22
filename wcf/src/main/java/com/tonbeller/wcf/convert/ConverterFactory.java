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
package com.tonbeller.wcf.convert;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.utils.ObjectFactory;
import com.tonbeller.wcf.utils.SoftException;

/**
 * Created on 05.11.2002
 * 
 * @author av
 */
public class ConverterFactory {
  private ConverterFactory() {
  }
  private static Logger logger = Logger.getLogger(ConverterFactory.class);

  /**
   * returns a new instance w/o caching
   */
  public static Converter instance(Formatter formatter) {
    URL configXml = ConverterFactory.class.getResource("config.xml");
    return instance(formatter, configXml);
  }

  public static Converter instance(Formatter formatter, URL configXml) {
    try {
      URL rulesXml = ConverterFactory.class.getResource("rules.xml");
      Converter conv = (Converter) ObjectFactory.instance(rulesXml, configXml);
      conv.setFormatter(formatter);
      return conv;
    } catch (SAXException e) {
      logger.error("?", e);
      throw new SoftException(e);
    } catch (IOException e) {
      logger.error("?", e);
      throw new SoftException(e);
    }
  }

}
