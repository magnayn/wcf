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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.bookmarks.Bookmarkable;
import com.tonbeller.wcf.controller.Controller;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * default implementation of a component
 * @author av
 */
public abstract class ComponentSupport
  implements Component, Form, HttpSessionBindingListener, Visible, RoleExprHolder, Bookmarkable {
  private static Logger logger = Logger.getLogger(ComponentSupport.class);
  private String id;
  private String roleExpr;

  private Dispatcher dispatcher = new DispatcherSupport();
  private Form form = new FormSupport();

  private Locale locale;
  private boolean visible = true;

  // needed for deregistering  
  private Controller controller;
  private Component parent;
  
  private boolean autoValidate;
  
  /**
   * creates a component
   * @param id the id for the session attribute of this component
   */
  public ComponentSupport(String id, Component parent) {
    this.id = id;
    this.parent = parent;
  }

  /**
   * returns a <code>Dispatcher</code> for this component. The dispatcher is local
   * to this component (i.e. the component may <code>clear()</code> it). The dispatcher
   * is registered with the Controller and functional.
   */
  public Dispatcher getDispatcher() {
    return dispatcher;
  }


  /**
   * called once when the component is created. Sets the locale of this component.
   * Called by the doEndTag of the ComponentTag, i.e. nested tags have been initialized.
   * @see #destroy
   * @see #valueBound
   * @see ComponentTag#doEndTag
   */
  public void initialize(RequestContext context) throws Exception {
    logger.info(id);
    locale = context.getLocale();
    controller = Controller.instance(context.getSession());
    controller.addRequestListener(this);
  }
  
  /**
   * called on session timeout or when the component is not used any more
   * @see #initialize
   * @see #valueUnbound
   */
  public void destroy(HttpSession session) throws Exception {
    logger.info(id);
    dispatcher.clear();
    if (controller == null)
      throw new IllegalStateException("not initialized");
    controller.removeRequestListener(this);
    controller = null;
  }

  public void request(RequestContext context) throws Exception {
    if (autoValidate)
      validate(context);
    dispatcher.request(context);
  }
  
  /**
   * empty
   */
  public void valueBound(HttpSessionBindingEvent e) {
  }

  /**
   * calls destroy on the component
   */
  public void valueUnbound(HttpSessionBindingEvent ev) {
    try {
      destroy(ev.getSession());
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(id, ex);
    }
  }

  /**
   * @return the id of this component (name of the session attribute).
   */
  public String getId() {
    return id;
  }

  /**
   * true, if this component should be rendered
   * @return boolean
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * true, if this component should be rendered
   * @param b
   */
  public void setVisible(boolean b) {
    visible = b;
  }

  /**
   * returns the <code>Form</code> for this component. The <code>Form</code> is local to
   * this component.
   *  
   * @return Form
   * @deprecated Component implements Form directly
   */
  public Form getForm() {
    return form;
  }

  /**
   * @param string
   */
  public void setId(String string) {
    id = string;
  }

  /**
   * @return
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * @return
   */
  public String getRoleExpr() {
    return roleExpr;
  }

  /**
   * @param roleExpr
   */
  public void setRoleExpr(String roleExpr) {
    this.roleExpr = roleExpr;
  }

  /**
   * returns a Map. Derived classe may use this map to store their properties. 
   * To avoid conflicts the name of an entry (key) should equal the property name (getter/setter).
   * @return a Map that contains the visible boolean
   */
  public Object retrieveBookmarkState(int levelOfDetail) {
    Map map = new HashMap();
    map.put("visible", new Boolean(isVisible()));
    return map;
  }

  /**
   * restores the visible attribute from the map
   * @param state a map
   */
  public void setBookmarkState(Object state) {
    if (!(state instanceof Map))
      return;
    Map map = (Map) state;
    Boolean b = (Boolean) map.get("visible");
    if (b != null)
      setVisible(b.booleanValue());
  }

  /**
   * @param listener
   */
  public void addFormListener(FormListener listener) {
    form.addFormListener(listener);
  }
  /**
   * @param listener
   */
  public void removeFormListener(FormListener listener) {
    form.removeFormListener(listener);
  }
  /**
   * @param context
   */
  public void revert(RequestContext context) {
    form.revert(context);
  }
  /**
   * @param context
   * @return
   */
  public boolean validate(RequestContext context) {
    return form.validate(context);
  }
  /**
   * @return Returns the parent.
   */
  public Component getParent() {
    return parent;
  }
  /**
   * sets the parent. Must be called before initialize()!!
   * @param parent The parent to set.
   */
  public void setParent(Component parent) {
    this.parent = parent;
  }

  /**
   * forwards to another page
   * 
   * @param uri if uri starts with "/" it will be interpreted
   * as context relative path (i.e. the context will be prepended),
   * otherwise it will be interpreted as relative to the current
   * page.
   */
  public void setNextView(String uri) {
    controller.setNextView(uri);
  }
  
  protected String getNextView() {
    return controller.getNextView();
  }
  
  /**
   * @return Returns the autoValidate.
   */
  public boolean isAutoValidate() {
    return autoValidate;
  }
  
  /**
   * @param autoValidate The autoValidate to set.
   */
  public void setAutoValidate(boolean autoValidate) {
    this.autoValidate = autoValidate;
  }
  
  public boolean isListeningTo(Map httpParams) {
    List list = dispatcher.findMatchingListeners(httpParams);
    return !list.isEmpty();
  }
}
