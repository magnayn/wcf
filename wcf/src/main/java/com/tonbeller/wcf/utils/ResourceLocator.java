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
package com.tonbeller.wcf.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.servlet.ServletContext;

/**
 * searches for resources in a locale specific way
 * 
 * @author av
 */
public class ResourceLocator {
  private static final String WEBKEY = ResourceLocator.class.getName();
  private Hashtable urlCache = new Hashtable();

  private ResourceLocator() {
  }

  static ResourceLocator instance(ServletContext context) {
    ResourceLocator loc = (ResourceLocator) context.getAttribute(WEBKEY);
    if (loc == null) {
      loc = new ResourceLocator();
      context.setAttribute(WEBKEY, loc);
    }
    return loc;
  }

  /**
   * Searches for a resource in a way similar to ResourceBundle.
   * It tries to match the language and country
   * codes. Example: with path "/test.xml", language "en" and country "US" 
   * the following resources will be searched:
   * <ol>
   *  <li>test_en_US.xml</li>
   *  <li>test_en.xml</li>
   *  <li>test.xml</li>
   * </ol>
   * <p>
   * Implementation note: the search results will be cached, so
   * you have to restart the servlet after adding or removing files.
   * @throws MissingResourceException if no resource is found
   */

  public static URL getResource(ServletContext context, Locale locale, String uri)
    throws MalformedURLException, MissingResourceException {
    return instance(context).findResource(context, locale, uri);
  }

  URL findResource(ServletContext context, Locale locale, String path)
    throws MalformedURLException, MissingResourceException {
    String ext = "";

    // check if we have an extension to remove
    if (path.lastIndexOf("/") < path.lastIndexOf('.')) {
      int pos = path.lastIndexOf('.');
      ext = path.substring(pos, path.length()); // including the dot
      path = path.substring(0, pos);
    }

    String test1 = path + "_" + locale.getLanguage() + "_" + locale.getCountry() + ext;
    if (urlCache.containsKey(test1)) {
      return (URL) urlCache.get(test1);
    }

    URL url = context.getResource(test1);
    if (url != null) {
      urlCache.put(test1, url);
      return url;
    }

    String test = path + "_" + locale.getLanguage() + ext;
    url = context.getResource(test);
    if (url != null) {
      urlCache.put(test1, url);
      return url;
    }

    test = path + ext;
    url = context.getResource(test);
    if (url != null) {
      urlCache.put(test1, url);
      return url;
    }

    throw new MissingResourceException("Resource \"" + path + "\" not found", ResourceLocator.class.getName(), path + ext);
  }

}
