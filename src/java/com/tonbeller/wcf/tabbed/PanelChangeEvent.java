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
package com.tonbeller.wcf.tabbed;

import java.util.EventObject;

import org.w3c.dom.Element;

/**
 * @author informs listeners about the panel that changed
 */
public class PanelChangeEvent extends EventObject {

  private Element currentPanel;
  private TabbedHandler handler;

  public PanelChangeEvent(TabbedHandler handler, Element currentPanel) {
    super(handler);
    this.handler = handler;
    this.currentPanel = currentPanel;
  }
  /**
   * @return Returns the currentPanelId.
   */
  public Element getCurrentPanel() {
    return currentPanel;
  }
  /**
   * @return Returns the handler.
   */
  public TabbedHandler getHandler() {
    return handler;
  }
}