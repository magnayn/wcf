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

/**
 * @author av
 */
public interface RoleExprHolder {

  /**
   * name of a role, optionally prefixed by "!". If the name of 
   * a role is returned, then members of that role may access
   * this component. If the role name is prefixed with "!", then 
   * everybody except members of that role may access this component.
   * 
   * @return "tomcat" to allow members of the role tomcat to access
   * this component, "!tomcat" to allow everybody except tomcat 
   * members access.
   */
  String getRoleExpr();
  void setRoleExpr(String expr);

}
