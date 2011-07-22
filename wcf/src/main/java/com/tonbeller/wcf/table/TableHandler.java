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
/*
 * ==================================================================== Copyright (c) 2003 TONBELLER
 * AG. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must include the
 * following acknowledgment: "This product includes software developed by TONBELLER AG
 * (http://www.tonbeller.com)" Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE TON BELLER AG OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * 
 */
package com.tonbeller.wcf.table;

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
 * Manages a TableComponent inside a FormComponent
 * 
 * @author av
 */
public class TableHandler extends NodeHandlerSupport {

  TableComponent table;

  FormComponent formComp;

  public void initialize(RequestContext context, XmlComponent xmlComp, Element element)
      throws Exception {
    super.initialize(context, xmlComp, element);
    // must access the bean, so it has to be a FormComponent
    formComp = (FormComponent) xmlComp;
    String ref = XoplonCtrl.getModelReference(element);
    Object bean = formComp.getBean();
    TableModel tm = (TableModel) PropertyUtils.getProperty(bean, ref);
    table = createTableComp(element.getAttribute("id"), formComp, tm);
    DefaultSelectionModel dsm = new DefaultSelectionModel();
    String selmode = element.getAttribute("selmode");
    if ("href".equals(selmode))
      dsm.setMode(SelectionModel.SINGLE_SELECTION_HREF);
    else if ("single".equals(selmode))
      dsm.setMode(SelectionModel.SINGLE_SELECTION);
    else if ("multi".equals(selmode))
      dsm.setMode(SelectionModel.MULTIPLE_SELECTION);
    else
      dsm.setMode(SelectionModel.NO_SELECTION);
    table.setSelectionModel(dsm);
    if (tm instanceof SelectionChangeListener)
        dsm.addSelectionListener((SelectionChangeListener) tm);
    table.setClosable(false);
    String border = element.getAttribute("border");
    if (border.length() > 0) 
      table.setBorder(border);
    String width = element.getAttribute("width");
    if (width.length() > 0) 
      table.setWidth(width);
    if("false".equals(element.getAttribute("colHeaders")))
      table.setColHeaders(false);
    // override renderId always
    table.setRenderId(element.getAttribute("id"));
    table.addFormListener(formComp);
    formComp.addFormListener(table);
    table.initialize(context);
  }

  protected TableComponent createTableComp(String id, FormComponent comp, TableModel tm) {
    return new TableComponent(id, formComp, tm);
  }

  public void destroy(HttpSession session) throws Exception {
    table.destroy(session);
    super.destroy(session);
  }

  public void render(RequestContext context) throws Exception {
    super.render(context);
    Element parent = getElement();
    DomUtils.removeChildElements(parent);
    Element child = table.render(context, parent.getOwnerDocument());
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
  public TableComponent getTable() {
    return table;
  }
}