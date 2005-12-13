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

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.tonbeller.wcf.charset.CharsetFilter;

/**
 * @author av
 * @since 12.07.2004
 */
public class UrlUtils {
  
  /**
   * URL encoding to be used with
   * @deprecated - use {@link CharsetFilter#getEncoding()} instead
   */
  public static final String URLENCODING = "ISO-8859-1";

  private UrlUtils() {
  }

  /**
   * ensures that the extension of the file portion matches ext. E.g.
   * forceExtension("/a/b.jsp?x=y", ".jspx") will return "/a/b.jspx?x=y".
   *
   * @param url the url to modify
   * @param ext the required extension
   */
  public static String forceExtension(String url, String ext) {
    return forceExtension(url, ".jsp", ext);
  }

  /**
   * true, if the request uri matches one of the url patterns.
   * @param req the request
   * @param urlPattern an url pattern may start with "*.ext" which is an extension
   * mapping or end with "/*" which is a prefix mapping, or "/" which matches
   * everything.
   */
  public static boolean matchPattern(HttpServletRequest req, String[] urlPattern) {
    String contextPath = req.getContextPath();
    String requestUri = req.getRequestURI();
    requestUri = requestUri.substring(contextPath.length());
    return matchPattern(requestUri, urlPattern);
  }

  /**
   * true if requestUri matches one of the urlPatterns
   * @param uri w/o contextPath
   * @param pattern
   */
  public static boolean matchPattern(String uri, String[] pattern) {
    for (int i = 0; i < pattern.length; i++) {
      if (matchPattern(uri, pattern[i]))
        return true;
    }
    return false;
  }

  /**
   * true if requestUri matches one of the urlPatterns
   * @param uri w/o contextPath
   * @param pattern
   */
  public static boolean matchPattern(String uri, String pattern) {
    if (pattern.equals("/*"))
      return true;
    
    if (pattern.equals(uri))
      return true;
    
    if (pattern.startsWith("*.")) {
      if (uri.endsWith(pattern.substring(2)))
        return true;
      return false;
    } 

    if (pattern.endsWith("/*")) {
      String prefix = pattern.substring(0, pattern.length() - 2);
      if (!uri.startsWith(prefix))
        return false;
      // "/a/b" matches "/a/b/*"
      if (uri.equals(prefix))
        return true;

      // "/a/bc" does not match "/a/b/*"
      // "/a/b.jsp" does not match "/a/b/*"
      // "/a/b/b" does match "/a/b/*"
      char c = uri.charAt(prefix.length());
      return c == '/';
    }
    
    return false;
  }

  /**
   * parses urlPatterns from a whitespace separated list.
   * @return null if urlPatternList is null
   */
  public static String[] parseUrlPatternList(String urlPatternList) {
    if (urlPatternList != null) {
      StringTokenizer st = new StringTokenizer(urlPatternList);
      int N = st.countTokens();
      String[] passThru = new String[N];
      for (int i = 0; i < N; i++)
        passThru[i] = st.nextToken();
      return passThru;
    }
    return null;
  }

  public static String forceExtension(String url, String old, String ext) {
    if (url != null && ext != null) {
      int dot = url.lastIndexOf(old);
      if (dot >= 0) {
        int qmk = url.indexOf('?', dot);
        String queryParam;
        if (qmk > 0)
          queryParam = url.substring(qmk);
        else
          queryParam = "";
        url = url.substring(0, dot) + ext + queryParam;
      }
    }
    return url;
  }

  public static String redirectURI(HttpServletRequest request, String uri) {
    if (uri.startsWith("/")) {
      StringBuffer sb = new StringBuffer();
      sb.append(request.getContextPath());
      sb.append(uri);
      uri = sb.toString();
    }
    return uri;
  }

}