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
package com.tonbeller.wcf.catedit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * creates the category DOM Element node
 * 
 * @author av
 */
public interface CategoryElementRenderer {
  Element render(RequestContext context, Document factory, Category cat);
}
