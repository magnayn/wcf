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
package com.tonbeller.wcf.tree;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.form.FormComponent;
import com.tonbeller.wcf.form.NodeHandlerSupport;
import com.tonbeller.wcf.form.XmlComponent;
import com.tonbeller.wcf.selection.DefaultSelectionModel;
import com.tonbeller.wcf.selection.SelectionChangeListener;
import com.tonbeller.wcf.selection.SelectionModel;
import com.tonbeller.wcf.ui.XoplonCtrl;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * Manages a TreeComponent inside a FormComponent
 * @author av
 */
public class TreeHandler extends NodeHandlerSupport {

  TreeComponent tree;
  FormComponent formComp;

  public void initialize(RequestContext context, XmlComponent xmlComp, Element element) throws Exception {
    super.initialize(context, xmlComp, element);

    // must access the bean, so it has to be a FormComponent
    formComp = (FormComponent) xmlComp;

    String ref = XoplonCtrl.getModelReference(element);
    Object bean = formComp.getBean();
    TreeModel tm = (TreeModel) PropertyUtils.getProperty(bean, ref);
    tree = new TreeComponent(element.getAttribute("id"), formComp, tm);

    DefaultSelectionModel dsm = new DefaultSelectionModel();
    String selmode = element.getAttribute("selmode");
    if ("href".equals(selmode))
      dsm.setMode(SelectionModel.SINGLE_SELECTION_HREF);
    else if ("single".equals(selmode))
      dsm.setMode(SelectionModel.SINGLE_SELECTION);
    else if ("multi".equals(selmode))
      dsm.setMode(SelectionModel.MULTIPLE_SELECTION);
    else if ("button".equals(selmode))
      dsm.setMode(SelectionModel.MULTIPLE_SELECTION_BUTTON);
    else
      dsm.setMode(SelectionModel.NO_SELECTION);
    tree.setSelectionModel(dsm);
    if (tm instanceof SelectionChangeListener)
      dsm.addSelectionListener((SelectionChangeListener) tm);

    // override border by setting an attribute
    String border = element.getAttribute("border");
    if (border.length() > 0)
      tree.setBorder(border);

    // override width by setting an attribute
    String width = element.getAttribute("width");
    if (width.length() > 0)
      tree.setWidth(width);

    // override renderId Attribute always
    tree.setRenderId(element.getAttribute("id"));

    // click in tree should validate the user input in form fields
    tree.addFormListener(formComp);
    formComp.addFormListener(tree);

    tree.initialize(context);
  }

  public void destroy(HttpSession session) throws Exception {
    tree.destroy(session);
    super.destroy(session);
  }

  public void render(RequestContext context) throws Exception {
    super.render(context);
    Element parent = getElement();
    DomUtils.removeChildElements(parent);
    Element child = tree.render(context, parent.getOwnerDocument());
    parent.appendChild(child);
  }

  /**
   * @return
   */
  public FormComponent getFormComp() {
    return formComp;
  }

  /**
   * @return
   */
  public TreeComponent getTree() {
    return tree;
  }
}
