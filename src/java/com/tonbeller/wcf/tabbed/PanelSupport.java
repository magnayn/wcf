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

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Element;

import com.tonbeller.wcf.component.FormListener;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.form.FormComponent;
import com.tonbeller.wcf.form.NodeHandler;
import com.tonbeller.wcf.form.XmlComponent;
import com.tonbeller.wcf.tree.TreeHandler;
import com.tonbeller.wcf.ui.XoplonCtrl;

/**
 * Default implementation of a <code>NodeHandler</code> for a panel of a
 * tabbed pane. <p/>PanelSupport provides access to the
 * <code>FormComponent</code> that contains the <code>tabbed</code> element,
 * access to the <code>TreeComponent</code> if one is present and access to
 * the <code>TabbedHandler</code> that manages this <code>panel</code> DOM
 * element.
 * 
 * @author av
 */
public class PanelSupport
    implements
      NodeHandler,
      PanelChangeListener,
      FormListener {
  private static Logger logger = Logger.getLogger(PanelSupport.class);

  protected FormComponent formComponent;
  protected TabbedHandler tabbedHandler;
  protected Element panelElement;

  public void initialize(RequestContext context, XmlComponent comp,
      Element element) throws Exception {
    this.formComponent = (FormComponent) comp;
    this.panelElement = element;

    formComponent.addFormListener(this);

    Element tabbedElement = (Element) element.getParentNode();
    tabbedHandler = (TabbedHandler) formComponent.getHandler(tabbedElement);
    tabbedHandler.addPanelChangedListener(this);
  }

  public void destroy(HttpSession session) throws Exception {
    formComponent.removeFormListener(this);
    tabbedHandler.removePanelChangedListener(this);
  }

  /**
   * searches for the firs &lt;xtree ...&gt; element in the DOM and returns
   * the handler associated with it. Derived classes may override this if 
   * they know the id of the tree element they want to listen to, e.g. using
   * FormComponent.getHandler(String id)
   */
  protected TreeHandler findTreeHandler(FormComponent fc) throws JaxenException {
    DOMXPath dx = new DOMXPath("//xtree");
    Element elem = (Element) dx.selectSingleNode(fc.getDocument());
    if (elem == null)
      return null;
    return (TreeHandler) fc.getHandler(elem);
  }


  public void render(RequestContext context) throws Exception {
  }

  /**
   * called when the user displays another panel
   */
  public void panelChanged(PanelChangeEvent event) {
  }

  /**
   * parse user input
   */
  public boolean validate(RequestContext context) {
    return true;
  }

  /**
   * format data for display
   */
  public void revert(RequestContext context) {
  }

  /**
   * @return Returns the formComponent.
   */
  public FormComponent getFormComponent() {
    return formComponent;
  }


  /**
   * @return Returns the tabbedHandler.
   */
  public TabbedHandler getTabbedHandler() {
    return tabbedHandler;
  }

  /**
   * @return Returns the hidden.
   */
  public boolean isHidden() {
    return XoplonCtrl.isHidden(panelElement);
  }

  /**
   * @param hidden
   *          The hidden to set.
   */
  public void setHidden(RequestContext context, boolean hidden) {
    tabbedHandler.setHidden(context, panelElement, hidden);
  }
}