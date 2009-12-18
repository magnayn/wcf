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
package com.tonbeller.wcf.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.FormListener;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.convert.ConvertException;
import com.tonbeller.wcf.format.FormatException;
import com.tonbeller.wcf.ui.XoplonCtrl;
import com.tonbeller.wcf.utils.DomUtils;
import com.tonbeller.wcf.utils.IdGenerator;
import com.tonbeller.wcf.utils.SoftException;
import com.tonbeller.wcf.utils.XoplonNS;
import com.tonbeller.wcf.wizard.PageListener;
import com.tonbeller.wcf.wizard.WizardPage;
import com.tonbeller.wcf.wizard.WizardPageSupport;

/**
 * Manges a DOM that contains xoplon controls like &lt;textField&gt; etc
 * @author av
 */
public class FormComponent extends XmlComponent implements FormListener, WizardPage {
  private boolean dirty = true;
  boolean haveError = false;
  Object bean;
  boolean bookmarkable = false;
  boolean finishButton = true;
  WizardPageSupport wizardPageSupport = new WizardPageSupport(this);

  private static Logger logger = Logger.getLogger(FormComponent.class);

  /**
   * creates a FormComponent with beanModel == this
   * @param id
   * @param document
   */
  public FormComponent(String id, Component parent, Document document) {
    this(id, parent, document, null);
    this.bean = this;
  }

  public FormComponent(String id, Component parent, Document document, Object bean) {
    super(id, parent, document);
    this.bean = bean;
    Element rootElem = document.getDocumentElement();
    if (rootElem.getAttribute("id").length() == 0)
      rootElem.setAttribute("id", id);
    new IdGenerator().generate(document, id + ".");
  }

  /**
   * fills the form with values from the bean
   */
  public void revert(RequestContext context) {
    super.revert(context);
    try {
      clearErrors();
      context.getConverter().revert(bean, getDocument());
      dirty = false;
      haveError = false;
    } catch (ConvertException e) {
      logger.error("exception caught", e);
      throw new SoftException(e);
    }
  }

  /**
   * fills bean and form from user input
   */
  public boolean validate(RequestContext context) {
    boolean success = super.validate(context);
    try {
      logger.info("enter");
      context.getConverter().validate(context.getParameters(), context.getFileParameters(),
          getDocument(), bean);
      return success;
    } catch (ConvertException e) {
      logger.error(null, e);
      throw new SoftException(e);
    } catch (FormatException e) {
      logger.info("invalid user input: " + e.getMessage());
      haveError = true;
      return false;
    }
  }

  /**
   * deferred ctor
   * @task check if the FormListener is removed from environment when this is removed from session
   */
  public void initialize(RequestContext context) throws Exception {
    super.initialize(context);
    initActionReferences();
    dirty = true;
    if (bean instanceof FormBean)
      ((FormBean) bean).setFormComponent(context, this);
  }

  /**
   * if there is no error, the form is filled with the current model values.
   * After that, the form is rendered.
   */
  public Document render(RequestContext context) throws Exception {
    if (dirty || !haveError)
      revert(context);
    return super.render(context);
  }

  /**
   * Sets the beanModel.
   * @param beanModel The beanModel to set
   */
  public void setBean(Object bean) {
    this.bean = bean;
    this.dirty = true;
  }

  /**
   * Returns the beanModel.
   * @return Object
   */
  public Object getBean() {
    return bean;
  }

  /**
   * sets an error message to a DOM element
   *
   * @param id the value of the <code>id</code> attribute of the
   * DOM Element that the error message will be attached to
   *
   * @param message the error message or null to remove the error attribute
   */
  public void setError(String id, String message) {
    Element elem = DomUtils.findElementWithId(id, getDocument().getDocumentElement());
    if (elem == null) {
      logger.error("No errorElement found with id=" + id + " in XML form");
      return;
    }
    if (message == null)
      DomUtils.removeAttribute(elem, "error");
    else
      elem.setAttribute("error", message);
    haveError = true;
  }

  /**
   * clears all error attributes in the DOM
   */
  public void clearErrors() {
    clearErrors(getDocument().getChildNodes());
    haveError = false;
  }

  protected void clearErrors(NodeList list) {
    int len = list.getLength();
    for (int i = 0; i < len; i++) {
      Node n = list.item(i);
      if (n.getNodeType() != Node.ELEMENT_NODE)
        continue;
      Element x = (Element) list.item(i);
      DomUtils.removeAttribute(x, "error");
      clearErrors(x.getChildNodes());
    }
  }

  /**
   * handler for an actionReference (e.g. a Button).
   */
  private class ActionReferenceListener implements RequestListener {
    private Element elem;
    private String methodPath;

    public ActionReferenceListener(Element elem, String methodPath) {
      this.elem = elem;
      this.methodPath = methodPath;
    }

    public void request(RequestContext context) throws Exception {
      // validate or revert?
      String action = elem.getAttribute("action");
      if (action.equals("revert"))
        revert(context);
      else if (action.equals("validate")) {
        if (!validate(context))
          return;
      }

      // to be compatible with previous WCF versions the state must be modified
      // before the actionReference is invoked.
      Object[] state = performButtonActions(context);
      if (!invokeActionReference(context)) {
        restoreButtonActions(context, state);
        return;
      }

      wizardPageSupport.fireWizardButton(context, methodName());
    }

    private String methodName() {
      String methodName = methodPath;
      int index = methodPath.lastIndexOf('.');
      if (index > 0)
        methodName = methodPath.substring(index + 1);
      return methodName;
    }

    private Object beanTarget() throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {
      Object target = bean;
      String methodName = methodPath;
      int index = methodPath.lastIndexOf('.');
      if (index > 0) {
        String beanPath = methodPath.substring(0, index);
        target = PropertyUtils.getProperty(target, beanPath);
      }
      return target;
    }

    /**
     * assumes that the actionReference will not throw an ActionReferenceException
     */
    private Object[] performButtonActions(RequestContext context) {
      Object[] state = new Object[2];
      // hide?
      state[0] = Boolean.valueOf(isVisible());
      if ("true".equals(elem.getAttribute("hide")))
        setVisible(false);
      // forward?
      state[1] = getNextView();
      String forward = elem.getAttribute("forward");
      if (forward != null && forward.length() > 0)
        setNextView(forward);
      // set attribute?
      String successAttr = elem.getAttribute("successAttr");
      if (successAttr.length() > 0)
        context.getRequest().setAttribute(successAttr, "true");
      return state;
    }

    /**
     * restores state after actionRefrence threw an ActionReferenceException
     */
    private void restoreButtonActions(RequestContext context, Object[] state) {
      setVisible(((Boolean) state[0]).booleanValue());
      setNextView((String) state[1]);
      String successAttr = elem.getAttribute("successAttr");
      if (successAttr.length() > 0)
        context.getRequest().removeAttribute(successAttr);
    }

    /**
     * invokes the actionReference on the bean.
     * @return false, if the actionReference threw an ActionReferenceException
     */
    private boolean invokeActionReference(RequestContext context) throws IllegalAccessException,
        InvocationTargetException {
      if (bean == null)
        return true;
      try {
        Object target = beanTarget();
        String methodName = methodName();
        Class c = target.getClass();
        Method m = c.getMethod(methodName, new Class[] { RequestContext.class});
        m.invoke(target, new Object[] { context});
        return true;
      } catch (NoSuchMethodException e) {
        logger.error("method not found: " + methodPath + " in " + bean);
        return true;
      } catch (InvocationTargetException e) {
        Throwable t = e.getTargetException();
        if (t instanceof ActionReferenceException)
          return false;
        throw e;
      }
    }
  }

  /**
   * finds all DOM elements with an <tt>actionReference</tt> attribute
   * and adds a Requesthandler for these.
   */
  private void initActionReferences() {
    try {
      DOMXPath dx = new DOMXPath("//*[@actionReference]");
      for (Iterator it = dx.selectNodes(getDocument()).iterator(); it.hasNext();) {
        Element elem = (Element) it.next();
        String methodPath = elem.getAttribute("actionReference");
        if ("none".equals(methodPath))
          continue;
        RequestListener rl = new ActionReferenceListener(elem, methodPath);
        String id = elem.getAttribute("id");
        getDispatcher().addRequestListener(id, null, rl);
      }
    } catch (JaxenException e) {
      logger.error(null, e);
    }
  }

  /**
   * adds all editable properties to the bookmark state. Editable
   * properties are addressed via the <code>modelReference</code>
   * attribute in the DOM.
   */
  public Object retrieveBookmarkState(int levelOfDetail) {
    if (!bookmarkable)
      return null;
    Map map = (Map) super.retrieveBookmarkState(levelOfDetail);
    try {
      DOMXPath dx = new DOMXPath("//*[@modelReference]");
      for (Iterator it = dx.selectNodes(getDocument()).iterator(); it.hasNext();) {
        Element elem = (Element) it.next();
        if ("false".equals(elem.getAttribute("bookmark")))
          continue;
        String ref = XoplonCtrl.getModelReference(elem);
        Object value = PropertyUtils.getProperty(bean, ref);
        map.put(ref, value);
      }
    } catch (JaxenException e) {
      logger.error("?", e);
    } catch (IllegalAccessException e) {
      logger.error("?", e);
    } catch (InvocationTargetException e) {
      logger.error("?", e);
    } catch (NoSuchMethodException e) {
      logger.error("?", e);
    }
    return map;
  }

  /**
   * restores all editable properties from the bookmark state. Editable
   * properties are addressed via the <code>modelReference</code>
   * attribute in the DOM.
   */
  public void setBookmarkState(Object state) {
    if (!bookmarkable)
      return;
    if (!(state instanceof Map))
      return;
    super.setBookmarkState(state);
    Map map = (Map) state;
    try {
      DOMXPath dx = new DOMXPath("//*[@modelReference]");
      for (Iterator it = dx.selectNodes(getDocument()).iterator(); it.hasNext();) {
        Element elem = (Element) it.next();
        if ("false".equals(elem.getAttribute("bookmark")))
          continue;
        // Properties may be added and removed over time.
        // So we react gentle if setting of a property fails
        try {
          String ref = XoplonCtrl.getModelReference(elem);
          Object value = map.get(ref);
          if (value != null)
            PropertyUtils.setProperty(bean, ref, value);
        } catch (IllegalAccessException e1) {
          logger.error(null, e1);
        } catch (InvocationTargetException e1) {
          logger.error(null, e1);
          logger.error(null, e1.getTargetException());
        } catch (NoSuchMethodException e1) {
          logger.warn(null, e1);
        }
      }
    } catch (JaxenException e) {
      logger.error(null, e);
    }
  }

  /**
   * @return
   */
  public boolean isBookmarkable() {
    return bookmarkable;
  }

  /**
   * @param b
   */
  public void setBookmarkable(boolean b) {
    bookmarkable = b;
  }

  public void addPageListener(PageListener l) {
    wizardPageSupport.addPageListener(l);
  }

  public void removePageListener(PageListener l) {
    wizardPageSupport.removePageListener(l);
  }

  /**
   * shows/hides form buttons automatically depending on the position of 
   * this form in a wizard.
   * This method sets the buttons with the following ids:
   * $id.back, $id.next, $id.cancel, $id.finish, $id.ok. 
   * <ul>
   * <li>Only page: ok/cancel
   * <li>First page: next/cancel/finish
   * <li>Intermediate page: back/next/cancel/finish
   * <li>Last page: back/cancel/finish
   * </ul>
   * Finish button is only displayed if the form component
   * supports this: supportsPrematureFinishBtn()    
   */
  public void pageAdded(WizardPagePosition pagePos) {
    boolean isBack = false;
    boolean isNext = false;
    boolean isFinish = false;
    boolean isOk = false;

    boolean isCancel = true;

    if (pagePos == WizardPagePosition.FIRST_PAGE) {
      isNext = true;
      isFinish = isFinishButton();
    } else if (pagePos == WizardPagePosition.MIDDLE_PAGE) {
      isNext = true;
      isBack = true;
      isFinish = isFinishButton();
    } else if (pagePos == WizardPagePosition.LAST_PAGE) {
      isNext = false;
      isBack = true;
      isFinish = true;
    } else if (pagePos == WizardPagePosition.SINGLE_PAGE) {
      isOk = true;
    }

    showButton("back", isBack);
    showButton("next", isNext);
    showButton("finish", isFinish);
    showButton("ok", isOk);
    showButton("cancel", isCancel);
  }

  private void showButton(String name, boolean show) {
    Element btn = getElement(getId() + "." + name);
    if (btn == null)
      return;
    
    if(XoplonNS.getAttribute(btn, "hidden")!=null)
      XoplonNS.removeAttribute(btn, "hidden");

    if (!show) 
      XoplonNS.setAttribute(btn, "hidden", "true");
  }
  
  public boolean isFinishButton() {
    return finishButton;
  }
  
  public void setFinishButton(boolean finishButton) {
    this.finishButton = finishButton;
  }
  
  public void pageSkipped() {
    
  }
}