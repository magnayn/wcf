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
package com.tonbeller.wcf.expr;

/**
 * @author av
 */
public interface ExprContext {
  /**
   * searches for bean in request, session, application contexts.
   */
  public Object findBean(String name);

  /**
   * places bean into session context
   * @param name name of the session attribute
   * @param bean the bean. If null, the attribute will be removed.
   */
  public void setBean(String name, Object bean);
}
