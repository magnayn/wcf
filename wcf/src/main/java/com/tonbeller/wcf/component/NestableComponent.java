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


/**
 * A Component that may be nested inside another component. It creates an Element instead
 * of a Document node, so multiple Element nodes may be combined in a Document. 
 */
public interface NestableComponent extends Component {
  
  // public Component getOwnerComponent();
  /**
   * returns an Element with ownerDocument == <code>factory</code>.
   */
  public Element render(RequestContext context, Document factory) throws Exception;
}
