/*
 * ====================================================================
 * Copyright (c) 2003 TONBELLER AG.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by
 *        TONBELLER AG (http://www.tonbeller.com)"
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE TON BELLER AG OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.tonbeller.tbutils.res.Resources;

/**
 * This request supports also forms with encoding "multipart/form-data"
 * that is used for file upload. For this kind of forms tomcat (J2EE reference)
 * request implementations always returns null from function getParameter()
 * <p>
 * MultiPartEnabledRequest is a decorator
 */
public class MultiPartEnabledRequest extends HttpServletRequestWrapper {

  private static Logger logger = Logger.getLogger(MultiPartEnabledRequest.class);
  private static Resources res = Resources.instance(MultiPartEnabledRequest.class);

  private boolean multipart;

  private Map fileItems = new HashMap();
  private Map httpParams = new HashMap();

  public MultiPartEnabledRequest(HttpServletRequest req) {
    super(req);
    this.multipart = FileUpload.isMultipartContent(req);
    if (multipart) {
      try {
        readHttpParams(req);
      } catch (FileUploadException e) {
        logger.error("", e);
        e.printStackTrace();
      }
    }
  }

  private void readHttpParams(HttpServletRequest req) throws FileUploadException {
    List all = uploadFiles(req);

    // read form fields
    for (Iterator it = all.iterator(); it.hasNext();) {
      FileItem item = (FileItem) it.next();

      if (item.isFormField()) {
        List valList = valueList(httpParams, item.getFieldName());
        if (req.getCharacterEncoding() != null) {
          try {
            valList.add(item.getString(req.getCharacterEncoding()));
          } catch (UnsupportedEncodingException e) {
            logger.error(null, e);
            valList.add(item.getString(/*encoding?*/));
          }
        }
        else
          valList.add(item.getString(/*encoding?*/));
      } else {
        List valList = valueList(fileItems, item.getFieldName());
        valList.add(item);
      }
    }

    // convert lists of values to arrays
    for (Iterator it = httpParams.keySet().iterator(); it.hasNext();) {
      String name = (String) it.next();
      List valList = (List) httpParams.get(name);
      httpParams.put(name, toStringArray(valList));
    }

    for (Iterator it = fileItems.keySet().iterator(); it.hasNext();) {
      String name = (String) it.next();
      List valList = (List) fileItems.get(name);
      fileItems.put(name, toFileItemArray(valList));
    }
  }

  private List valueList(Map params, String name) {
    List valList = (List) params.get(name);
    if (valList == null) {
      valList = new ArrayList();
      params.put(name, valList);
    }
    return valList;
  }

  private String[] toStringArray(List valList) {
    String[] vals = new String[valList.size()];
    for (int i = 0; i < vals.length; i++)
      vals[i] = (String) valList.get(i);
    return vals;
  }

  private FileItem[] toFileItemArray(List valList) {
    FileItem[] vals = new FileItem[valList.size()];
    for (int i = 0; i < vals.length; i++)
      vals[i] = (FileItem) valList.get(i);
    return vals;
  }

  private List uploadFiles(HttpServletRequest req) throws FileUploadException {

    DiskFileUpload upload = new DiskFileUpload();

    try {
      upload.setSizeThreshold(res.getInteger("file.upload.size.threshold"));
    } catch (MissingResourceException e) {
      // use defaults
    }

    try {
      upload.setSizeMax(res.getInteger("file.upload.size.max"));
    } catch (MissingResourceException e) {
      // use defaults
    }

    try {
      upload.setRepositoryPath(res.getString("file.upload.repository"));
    } catch (MissingResourceException e) {
      // use defaults
    }

    List all = new DiskFileUpload().parseRequest(req);
    return all;
  }

  public boolean isMultipart() {
    return multipart;
  }

  public String getParameter(String name) {
    if (!isMultipart())
      return super.getParameter(name);

    String[] vals = (String[]) httpParams.get(name);
    if (vals == null)
      return null;

    return vals[0];
  }

  public FileItem getFileParameter(String name) {
    FileItem[] vals = (FileItem[]) fileItems.get(name);
    if (vals == null)
      return null;

    return vals[0];
  }

  public Map getParameterMap() {
    if (!isMultipart())
      return super.getParameterMap();

    return new HashMap(httpParams);
  }

  public Map getFileParameterMap() {
    return new HashMap(fileItems);
  }

  public Enumeration getParameterNames() {
    if (!isMultipart())
      return super.getParameterNames();

    return new Vector(httpParams.keySet()).elements();
  }

  public Enumeration getFileParameterNames() {
    return new Vector(fileItems.keySet()).elements();
  }

  public String[] getParameterValues(String name) {
    if (!isMultipart())
      return super.getParameterValues(name);

    return (String[]) httpParams.get(name);
  }

  public FileItem[] getFileParameterValues(String name) {
    return (FileItem[]) fileItems.get(name);
  }

}