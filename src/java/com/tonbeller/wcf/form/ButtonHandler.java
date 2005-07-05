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

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.scroller.Scroller;
import com.tonbeller.wcf.ui.Button;

/**
 * Default ButtonHandler.
 * 
 * TODO should be unified with ActionReference Code!
 * 
 * Supports the following attributes
 * <dl>
 *   <dt><b>action="revert"</b><br><dd>
 *      calls revert() on the <code>Form</code>, i.e. formats
 *      the data from the bean for presentation.
 * 
 *   <dt><b>action="validate"</b><br><dd>
 *       calls validate() on the <code>Form</code>, i.e. parses
 *       user input and writes the values to the bean properties
 * 
 *   <dt><b>forward="/some/page.jsp"</b><br><dd>
 *       if validation succeeds forwards to another page. Paramers make sense here.
 * 
 *   <dt><b>hide="true"</b><br><dd>
 *       if validation succeeds hides the component
 * 
 *   <dt><b>successAttr="name-of-request-attribute"</b><br><dd>
 *       if validation succeeds a request attribute with value <code>true</code>
 *       is created.
 *  
 *   <dt><b>scroller="true"</b><br><dd>
 *       keeps the current browser window scroll position after the 
 *       request is finished
 * </dl>
 * <p>
 * 
 * @author av
 */

public class ButtonHandler implements NodeHandler, RequestListener {

  private XmlComponent comp;
  private Element element;
  private boolean pressed = false;
  private boolean success = true;

  public static final String NO_ACTION = "";
  public static final String VALIDATE_ACTION = "validate";
  public static final String REVERT_ACTION = "revert";
  private static Logger logger = Logger.getLogger(ButtonHandler.class);


  public void initialize(RequestContext context, XmlComponent comp, Element element) {
    this.comp = comp;
    this.element = element;
    comp.getDispatcher().addRequestListener(Button.getId(element), null, this);
  }
  
  public void destroy(HttpSession session) {
  }
  
  /**
   * calls revert/validate on the form and sets the next view
   */
  public void request(RequestContext context) throws Exception {
    if(element.getAttribute("scroller")!=null)
      Scroller.enableScroller(context);
      
    pressed = true;
    success = true;
    String action = element.getAttribute("action");
    if (action.equals("revert"))
      comp.revert(context);
    else if (action.equals("validate"))
      success = comp.validate(context);
    if (success) {

      boolean hide = "true".equals(element.getAttribute("hide"));
      if (hide)
        comp.setVisible(false);

      String forward = element.getAttribute("forward");
      if (forward != null && forward.length() > 0)
        comp.setNextView(forward);

      String successAttr = element.getAttribute("successAttr");
      if (successAttr.length() > 0)
        context.getRequest().setAttribute(successAttr, "true");
    }
  }

  /** does nothing */
  public void render(RequestContext context) throws Exception {
  }

}
