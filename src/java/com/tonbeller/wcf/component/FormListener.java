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

import com.tonbeller.wcf.controller.RequestContext;

/**
 * FormListeners are notified about form changes.
 * @author av
 */
public interface FormListener {
  /** 
   * validates user input and updates the model
   * @return true if validation was successful
   */
  boolean validate(RequestContext context);
  
  /**
   * reverts this component to model values
   */
  void revert(RequestContext context);
}
