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

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.bookmarks.BookmarkManager;
import com.tonbeller.wcf.bookmarks.Bookmarkable;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * initializes the component during doEndTag()
 * 
 * @author av
 */
public abstract class ComponentTag extends TagSupport {
  boolean objectCreated;
  boolean visible = true;
  boolean validate = false;
  String role;

  private static Logger logger = Logger.getLogger(ComponentTag.class);

  /**
   * creates the component. Called whenever the object does not exist in the
   * session
   * @return the object that will be placed into the session
   */
  protected abstract Component createComponent(RequestContext context) throws Exception;

  /**
   * Creates the component if it does not exsist in the session. If the
   * component is created the body of the tag is evaluated, else its skipped.
   */
  public int doStartTag() throws JspException {
    logger.info(id);
    try {
      objectCreated = false;
      HttpSession session = pageContext.getSession();
      if (session.getAttribute(getId()) == null) {
        if (logger.isInfoEnabled())
          logger.info("creating " + id);
        Component comp = createComponent(RequestContext.instance());
        session.setAttribute(getId(), comp);
        objectCreated = true;
        return EVAL_BODY_INCLUDE;
      }
      return SKIP_BODY;
    } catch (Exception e) {
      logger.error("trouble creating " + getId(), e);
      throw new JspException(e);
    }
  }

  /**
   * returns the component
   */
  public Component getComponent() {
    return (Component) pageContext.getSession().getAttribute(getId());
  }

  /**
   * returns true if the previous call to doStartTag has created the object, so further
   * initialization can be done.
   */
  public boolean isObjectCreated() {
    return objectCreated;
  }


  public int doEndTag() throws JspException {
    logger.info(id);
    if (objectCreated) {
      try {
        Component comp = getComponent();
        comp.initialize(RequestContext.instance());
        comp.setVisible(visible);
        if (comp instanceof ComponentSupport)
          ((ComponentSupport)comp).setAutoValidate(validate);
        if (comp instanceof RoleExprHolder)
           ((RoleExprHolder) comp).setRoleExpr(role);
        if (comp instanceof Bookmarkable)
          BookmarkManager.instance(pageContext.getSession()).restoreAttributeState(id);
      } catch (Exception e) {
        logger.error("trouble initializing " + getId(), e);
        throw new JspException(e.toString(), e);
      }
    }
    return super.doEndTag();
  }

  /**
   * @param b
   */
  public void setVisible(boolean b) {
    visible = b;
  }

  /**
   * @param role
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * @param validate The validate to set.
   */
  public void setValidate(boolean validate) {
    this.validate = validate;
  }
}
