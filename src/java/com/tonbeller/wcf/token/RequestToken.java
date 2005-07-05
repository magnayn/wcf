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
 * Created on 20.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.tonbeller.wcf.token;

import javax.servlet.http.HttpSession;

public class RequestToken {
  static final String WEBKEY = RequestToken.class.getName();
  private String token;
  private String page;
  private String httpParameterName;

  RequestToken() {
  }

  public String getPage() {
    return page;
  }

  public String getToken() {
    return token;
  }

  public void setPage(String string) {
    page = string;
  }

  public void setToken(String string) {
    token = string;
  }

  public String getHttpParameterName() {
    return httpParameterName;
  }

  public void setHttpParameterName(String string) {
    httpParameterName = string;
  }

  /**
   * allow browser navigation in the next request.
   */
  public void clear() {
    setToken(null);
  }

  /**
   * appends "?token=wcf1234" or "&token=wcf1234" to url
   * @param url the url to which the token parameter should be added.
   */
  public String appendHttpParameter(String url) {
    if (token == null || httpParameterName == null)
      return url;
    StringBuffer sb = new StringBuffer();
    sb.append(url);
    if (url.indexOf('?') >= 0)
      sb.append('&');
    else
      sb.append('?');
    sb.append(httpParameterName);
    sb.append('=');
    sb.append(token);
    return sb.toString();
  }


  public static RequestToken instance(HttpSession session) {
    RequestToken s = (RequestToken) session.getAttribute(WEBKEY);
    if (s == null) {
      s = new RequestToken();
      session.setAttribute(WEBKEY, s);
    }
    return s;
  }

}