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
package com.tonbeller.wcf.changeorder;

/**
 * does nothing
 * 
 * @author av
 */
public class DefaultChangeOrderModel implements ChangeOrderModel {

  /**
   * returns false
   */
  public boolean mayMove(Object scope, Object node) {
    return false;
  }

  /**
   * does nothing
   */
  public void move(Object scope, Object item, int oldIndex, int newIndex) {
  }

}
