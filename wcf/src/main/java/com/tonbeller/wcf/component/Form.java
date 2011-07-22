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
 * Sends events to FormListeners, e.g.
 * if a button one component calls validate() on the <code>Form</code>, 
 * all <code>FormListeners</code> will receive this event.
 * <p>
 * <code>Form</code> instances may listen to each other. So (for example) 
 * expanding a node in a tree component may also validate text input
 * in a form.
 * <code>Form</code> may listen to each other without endless recursion,
 * e.g. form1 may listen to form2 and form2 to form1.
 * <p>
 * <code>Form</code> ensures that a <code>FormListener</code> can not
 * be registered more than once.
 * @see FormListener
 *   
 * @author av
 */
public interface Form extends FormListener {
  void addFormListener(FormListener listener);
  void removeFormListener(FormListener listener);
}
