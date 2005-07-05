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
package com.tonbeller.wcf.ui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.utils.XoplonNS;

public class FileUploadCtrl extends XoplonCtrl {

  public static final String NODENAME = "fileUpload";

  public static boolean isFileUpload(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function */
  public static Element createFileUpload(Document doc) {
    Element fu = Item.createItem(doc, NODENAME);
    return fu;
  }

  /**
   * client file path
   */
  public static void setFileName(Element element, String fileName) {
    XoplonNS.setAttribute(element, "filename", fileName);
  }

  /**
   * client file path
   */
  public static String getFileName(Element element) {
    return XoplonNS.getAttribute(element, "filename");
  }

  /**
   * maximum accepted file size in bytes
   */
  public static void setMaxLength(Element element, long maxLength) {
    XoplonNS.setAttribute(element, "maxlength", String.valueOf(maxLength));
  }

  /**
   * maximum accepted file size in bytes
   */
  public static String getMaxLength(Element element) {
    return XoplonNS.getAttribute(element, "maxlength");
  }

  /**
   * Content type pattern of files accepted as input, e.g. text/*
   */
  public static void setAccept(Element element, String accept) {
    XoplonNS.setAttribute(element, "accept", accept);
  }

  /**
   * Content type pattern of files accepted as input, e.g. text/*
   */
  public static String getAccept(Element element) {
    return XoplonNS.getAttribute(element, "accept");
  }

}