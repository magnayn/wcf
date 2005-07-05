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
package com.tonbeller.wcf.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Support class for building WizardPage implementations. 
 * 
 * @author av
 */
public class WizardPageSupport {
  WizardPage source;
  ArrayList listeners = new ArrayList();

  public WizardPageSupport(WizardPage source) {
    this.source = source;
  }

  public void fireNext(RequestContext context) throws Exception {
    for (Iterator it = iterator(); it.hasNext();)
      ((PageListener) it.next()).onNext(context);
  }

  public void fireBack(RequestContext context) throws Exception {
    for (Iterator it = iterator(); it.hasNext();)
      ((PageListener) it.next()).onBack(context);
  }

  public void fireFinish(RequestContext context) throws Exception {
    for (Iterator it = iterator(); it.hasNext();)
      ((PageListener) it.next()).onFinish(context);
  }

  public void fireCancel(RequestContext context) throws Exception {
    for (Iterator it = iterator(); it.hasNext();)
      ((PageListener) it.next()).onCancel(context);
  }

  private Iterator iterator() {
    if (listeners.size() == 0)
      return Collections.EMPTY_LIST.iterator();
    List copy = (List) listeners.clone();
    return copy.iterator();
  }

  public void addPageListener(PageListener l) {
    listeners.add(l);
  }

  public void removePageListener(PageListener l) {
    listeners.remove(l);
  }

  public void fireWizardButton(RequestContext context, String methodName) throws Exception {
    if ("onNext".equals(methodName))
      fireNext(context);
    else if ("onBack".equals(methodName))
      fireBack(context);
    else if ("onCancel".equals(methodName))
      fireCancel(context);
    else if ("onFinish".equals(methodName))
      fireFinish(context);
  }

}