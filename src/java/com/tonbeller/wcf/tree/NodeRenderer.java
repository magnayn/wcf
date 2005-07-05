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
package com.tonbeller.wcf.tree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Created on 10.12.2002
 * 
 * @author av
 */
public interface NodeRenderer {
  public static final String DEFAULT_NODE_ELEMENT_NAME = "tree-node";
  Element renderNode(RequestContext context, Document factory, Object node);
}
