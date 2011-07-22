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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tonbeller.wcf.controller.Controller;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.form.NodeHandler;
import com.tonbeller.wcf.form.XmlComponent;
import com.tonbeller.wcf.ui.XoplonCtrl;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * A <code>NodeHandler</code> that is associated with a &lt;tabbed&gt; element
 * and manages visibility of its &lt;panel&gt; children.
 * 
 * @author av
 */
public class TabbedHandler implements NodeHandler, RequestListener {
  private NodeList panels;
  private Dispatcher dispatcher = new DispatcherSupport();
  private static Logger logger = Logger.getLogger(TabbedHandler.class);
  private Controller controller;
  private XmlComponent xmlComponent;
  private ArrayList panelChangeListeners = new ArrayList();
  // null == all panels are hidden, none can be displayed
  private Element currentPanel = null;
  private Element tabbed = null;

  public void initialize(RequestContext context, XmlComponent comp, Element tabbed) {
    this.xmlComponent = comp;
    this.tabbed = tabbed;
    this.controller = Controller.instance(context.getSession());
    controller.addRequestListener(dispatcher);
    panels = tabbed.getElementsByTagName("xpanel");
    for (int i = 0; i < panels.getLength(); i++) {
      Element panel = (Element) panels.item(i);
      RequestListener rl = new TabbedPanelListener(panel);
      String id = panel.getAttribute("id");
      dispatcher.addRequestListener(id, null, rl);

      // hide tabs that the user is not allowed to see
      String role = panel.getAttribute("role");
      if (role.length() > 0 && !context.isUserInRole(role))
        XoplonCtrl.setHidden(panel, true);
      if ("true".equals(panel.getAttribute("current")))
        currentPanel = panel;
    }
  }

  public void destroy(HttpSession session) {
    dispatcher.clear();
    controller.removeRequestListener(dispatcher);
    controller = null;
  }

  public void request(RequestContext context) throws Exception {
    dispatcher.request(context);
  }

  /** does nothing */
  public void render(RequestContext context) throws Exception {
  }

  public void addPanelChangedListener(PanelChangeListener l) {
    panelChangeListeners.add(l);
  }

  public void removePanelChangedListener(PanelChangeListener l) {
    panelChangeListeners.remove(l);
  }

  protected void fireChanged(Element currentPanel) {
    if (panelChangeListeners.size() > 0) {
      PanelChangeEvent event = new PanelChangeEvent(this, currentPanel);
      List copy = (List) panelChangeListeners.clone();
      for (Iterator it = copy.iterator(); it.hasNext();)
        ((PanelChangeListener) it.next()).panelChanged(event);
    }
  }

  /**
   * sets the current panel. If the panel is hidden, the hidden attribute is
   * removed (the panel will be visible). Fires a PanelChangeEvent.
   * 
   * @param context
   * @param currentId
   */
  public void setCurrentPanel(RequestContext context, Element newCurrentPanel) {
    if (!xmlComponent.validate(context))
      return;
    internalSetCurrent(newCurrentPanel);
    // allow the panel hander to update model values
    fireChanged(currentPanel);
    // and now display the updated model values
    xmlComponent.revert(context);
  }

  private void internalSetCurrent(Element newCurrentPanel) {
    // remove current attribute
    if (currentPanel != null) {
      DomUtils.removeAttribute(currentPanel, "current");
      currentPanel.setAttribute("children-hidden", "true");
    }
    // set current attribute
    currentPanel = newCurrentPanel;
    if (currentPanel != null) {
      currentPanel.setAttribute("current", "true");
      DomUtils.removeAttribute(currentPanel, "children-hidden");
      // current panel must be visible
      XoplonCtrl.setHidden(currentPanel, false);
    }
  }

  /**
   * hides or unhides the panel with panelId. If the current 
   * panel gets hidden, the first non-hidden panel will become 
   * the current panel.
   * <p />
   * If the panel contains a <code>role</code> attribute, the call
   * is ignored unless the current user is a member of that role.
   * 
   * @param context
   * @param panelId
   */
  public void setHidden(RequestContext context, Element panel, boolean hidden) {
    String role = panel.getAttribute("role");
    if (!context.isUserInRole(role))
      return;
    
    XoplonCtrl.setHidden(panel, hidden);
    if (hidden && panel.equals(currentPanel)) {
      // dont fire events yet
      internalSetCurrent(null);
      panels = tabbed.getElementsByTagName("xpanel");
      for (int i = 0; i < panels.getLength(); i++) {
        Element e = (Element) panels.item(i);
        if (!XoplonCtrl.isHidden(e)) {
          // found new current, fire events now
          setCurrentPanel(context, e);
          break;
        }
      }
    } else if (!hidden && currentPanel == null) {
      // all panels have been hidden, show this one
      setCurrentPanel(context, panel);
    }
  }

  class TabbedPanelListener implements RequestListener {
    private Element panel;

    TabbedPanelListener(Element panel) {
      this.panel = panel;
    }

    public void request(RequestContext context) throws Exception {
      setCurrentPanel(context, panel);
    }
  }

  /**
   * returns the NodeHandler for the panel or null if there was no NodeHandler.
   * NodeHandlers are registered via the <code>handler</code> attribute in the
   * XML file describing the form.
   * 
   * @param panelId
   *          id attribute of the panel element
   * @return the node handler or null
   */
  public NodeHandler getPanelHandler(String panelId) {
    return xmlComponent.getHandler(panelId);
  }

}