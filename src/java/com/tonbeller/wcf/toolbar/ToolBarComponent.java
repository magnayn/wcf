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
package com.tonbeller.wcf.toolbar;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Toolbar element like button, separator etc
 * @author av
 */
public interface ToolBarComponent {

  /**
   * renders the toolbar button
   */
  void render(RequestContext context, Element parent) throws Exception;

  /**
   * deferred ctor - initializes the button once after instantiation
   */
  void initialize(RequestContext context, ToolBar owner);
  
  /**
   * an expression that evaluates to a boolean. If set, the button will be visible
   * only if the boolen is true
   */
  void setVisibleExpr(String expr);

  /**
   * true if this button is visible
   */
  boolean isVisible(RequestContext context);

  /**
   * paints some space
   */
  boolean isSeparator();

  /**
   * default visible state in case no visibleExpr is set
   */
  void setVisible(boolean b);

  /**
   * default visible state in case no visibleExpr is set
   */
  boolean isVisible();
 
  /**
   * must be unique within the toolbar. if null, the toolbar element
   * will not be scriptable.
   */
  String getId();
  
}
