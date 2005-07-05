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

import com.tonbeller.wcf.controller.RequestContext;

/**
 * visibility of a toolbar button is computed as followd 
   * <ol>
   * <li>if a role attribute was supplied, and the user is authenticated, and the user is not member of that role,
   *     the button is not shown</li>
   * <li>otherwise, if visibleExpr is given (either a ${...} EL expression or "true" or "false"), then
   *     its result is returned</li>
   * <li>otherwise, the value of the boolean visible property is returned which
   * defaults to true.</li>
   * </ol>
 */
public abstract class ToolBarComponentSupport implements ToolBarComponent {

  String role;
  String visibleExpr;
  boolean visible = true;
  String id;
  
  /**
   * returns true if this Component is visible.
   * Visability is computed as follows
   * <ol>
   * <li>If the <code>visible</code> property is set to false, the button is not visible.
   * <li>if a role attribute was supplied, and the user is authenticated, and the user is not member of that role,
   *     the button is not shown</li>
   * <li>otherwise, if visibleExpr is given (either a ${...} EL expression or "true" or "false"), then
   *     its result is returned</li>
   * <li>otherwise, the value of the boolean visible property is returned which
   * defaults to true.</li>
   * </ol>
   */
  public boolean isVisible(RequestContext context) {
    if (!visible)
      return false;

    // if !allowed, the user can never see this button
    if (!context.isUserInRole(role))
      return false;

    // otherwise evaluate visibleExpr
    if (visibleExpr != null) {
      if ("true".equals(visibleExpr))
        return true;
      if ("false".equals(visibleExpr))
        return false;
      Object val = context.getModelReference(visibleExpr);
      String s = String.valueOf(val);
      boolean b = Boolean.valueOf(s).booleanValue();
      return b;
    }
    
    return true;
  }

  /**
   * @return
   */
  public String getVisibleExpr() {
    return visibleExpr;
  }

  /**
   * @param string
   */
  public void setVisibleExpr(String string) {
    visibleExpr = string;
  }

  /**
   * @return
   */
  public String getRole() {
    return role;
  }

  /**
   * @param role
   */
  public void setRole(String role) {
    this.role = role;
  }

  public boolean isVisible() {
    return visible;
  }
  public void setVisible(boolean visible) {
    this.visible = visible;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
}
