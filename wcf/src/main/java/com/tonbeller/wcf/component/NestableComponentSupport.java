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
package com.tonbeller.wcf.component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.utils.XmlUtils;

/**
 * default implementation of NestableComponent
 */
public abstract class NestableComponentSupport
  extends ComponentSupport
  implements NestableComponent {

  public NestableComponentSupport(String id, Component parent) {
    super(id, parent);
  }

  /**
   * renders the component using its NestableComponent.render() method
   * @see NestableComponent#render
   * @see Renderable#render
   */
  public Document render(RequestContext context) throws Exception {
    Document doc = XmlUtils.createDocument();
    Element elem = render(context, doc);
    doc.appendChild(elem);
    return doc;
  }

}
