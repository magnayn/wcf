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
 * creates an Element "tree-node" with an attribute "label", whose value is node.toString()
 * @author av
 */
public class DefaultNodeRenderer implements NodeRenderer {
  protected String nodeElementName = DEFAULT_NODE_ELEMENT_NAME;
  private LabelProvider labelProvider;

  public DefaultNodeRenderer() {
    labelProvider = new DefaultLabelProvider();
  }

  public DefaultNodeRenderer(LabelProvider labelProvider) {
    this.labelProvider = labelProvider;
  }
  
  public Element renderNode(RequestContext context, Document factory, Object node) {
    Element nodeElem = factory.createElement(nodeElementName);
    nodeElem.setAttribute("label", labelProvider.getLabel(node));
    return nodeElem;
  }

  /**
   * Returns the nodeElementName.
   * @return String
   */
  public String getNodeElementName() {
    return nodeElementName;
  }

  /**
   * Sets the nodeElementName.
   * @param nodeElementName The nodeElementName to set
   */
  public void setNodeElementName(String nodeElementName) {
    this.nodeElementName = nodeElementName;
  }

  /**
   * @return Returns the labelProvider.
   */
  public LabelProvider getLabelProvider() {
    return labelProvider;
  }
  /**
   * @param labelProvider The labelProvider to set.
   */
  public void setLabelProvider(LabelProvider labelProvider) {
    this.labelProvider = labelProvider;
  }
}
