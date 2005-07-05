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
package com.tonbeller.wcf.form;

import org.apache.log4j.Logger;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.ComponentSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.utils.DomUtils;
import com.tonbeller.wcf.utils.SoftException;

/**
 * a simple yes/no query component
 */

public class ConfirmComponent extends ComponentSupport {
  Document document;
  Element title;
  Element body;
  RequestListener acceptHandler;
  RequestListener cancelHandler;
  public static final String SESSIONKEY = "confirmForm";
  public static final String BUNDLE = "com.tonbeller.wcf.form.resources";
  private static final Logger logger = Logger.getLogger(ConfirmComponent.class);

  public static ConfirmComponent instance() {
    return (ConfirmComponent) RequestContext.instance().getSession().getAttribute(SESSIONKEY);
  }

  public void initialize(RequestContext context) throws Exception {
    super.initialize(context);
    FormDocument.replaceI18n(context, document, BUNDLE);
  }

  public ConfirmComponent(String id, Component parent, Document document) {
    super(id, parent);
    this.document = document;

    try {
      title = (Element) new DOMXPath("//title").selectSingleNode(document);
      body = (Element) new DOMXPath("//td[@id='body']").selectSingleNode(document);
    } catch (JaxenException e) {
      logger.error(null, e);
      throw new SoftException(e);
    }

    getDispatcher().addRequestListener("confirm.accept", null, new RequestListener() {
      public void request(RequestContext context) throws Exception {
        setVisible(false);
        if (acceptHandler != null)
          acceptHandler.request(context);
      }
    });

    getDispatcher().addRequestListener("confirm.cancel", null, new RequestListener() {
      public void request(RequestContext context) throws Exception {
        setVisible(false);
        if (cancelHandler != null)
          cancelHandler.request(context);
      }
    });

    setVisible(false);
  }

  public Document render(RequestContext context) throws Exception {
    return document;
  }

  /**
   * creates a simple yes/no form
   * @param title title of the form
   * @param text text of the body
   * @param accept action for OK button or null
   * @param cancel action for Cancel button or null
   */
  public void show(String title, String text, RequestListener accept, RequestListener cancel) {
    // remove stuff from previous call
    DomUtils.removeChildNodesExceptAttributes(body);
    this.title.setAttribute("value", title);
    DomUtils.setText(body, text);
    this.acceptHandler = accept;
    this.cancelHandler = cancel;
    setVisible(true);
  }

  /**
   * crates a yes/no dialog with a list
   * @param title title of the dialog
   * @param text1 text before the list
   * @param list the list items 
   * @param text2 text after the list
   * @param accept action for OK button or null
   * @param cancel action for Cancel button or null
   */
  public void show(String title, String text1, String[] list, String text2, RequestListener accept,
      RequestListener cancel) {
    DomUtils.removeChildNodesExceptAttributes(body);
    this.title.setAttribute("value", title);

    Text t1 = document.createTextNode(text1);
    body.appendChild(t1);
    Element ul = document.createElement("ul");
    body.appendChild(ul);
    for (int i = 0; i < list.length; i++) {
      Element li = document.createElement("li");
      ul.appendChild(li);
      li.appendChild(document.createTextNode(list[i]));
    }
    Text t2 = document.createTextNode(text2);
    body.appendChild(t2);

    this.acceptHandler = accept;
    this.cancelHandler = cancel;
    setVisible(true);
  }

  /**
   * crates a yes/no dialog with a list
   * @param title title of the dialog
   * @param textA text before list A
   * @param listA the first group of list items 
   * @param textB text before list B
   * @param listB the second group of list items
   * @param text text after the list
   * @param accept action for OK button or null
   * @param cancel action for Cancel button or null
   */
  public void show(String title, String textA, String[] listA, String textB, String[] listB,
      String text, RequestListener accept, RequestListener cancel) {
    DomUtils.removeChildNodesExceptAttributes(body);
    this.title.setAttribute("value", title);

    appendGroup(document, textA, listA);
    appendGroup(document, textB, listB);

    Text t = document.createTextNode(text);
    body.appendChild(t);

    this.acceptHandler = accept;
    this.cancelHandler = cancel;
    setVisible(true);
  }

  private void appendGroup(Document doc, String text, String[] list) {
    if (list == null || list.length == 0)
      return;

    Text t = document.createTextNode(text);
    body.appendChild(t);
    Element ul = document.createElement("ul");
    body.appendChild(ul);
    for (int i = 0; i < list.length; i++) {
      Element li = document.createElement("li");
      ul.appendChild(li);
      li.appendChild(document.createTextNode(list[i]));
    }
  }
}
