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
package com.tonbeller.wcf.selection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.tonbeller.wcf.component.Form;
import com.tonbeller.wcf.component.FormListener;
import com.tonbeller.wcf.component.RenderListener;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.convert.BooleanConverter;
import com.tonbeller.wcf.convert.CheckBoxConverter;
import com.tonbeller.wcf.convert.RadioButtonConverter;
import com.tonbeller.wcf.ui.CheckBox;
import com.tonbeller.wcf.ui.Item;
import com.tonbeller.wcf.ui.RadioButton;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * Selection Manager
 * 
 * @author av
 */
public class SelectionMgr implements FormListener, RenderListener {

  String groupId = DomUtils.randomId();
  BooleanConverter radioConv = new RadioButtonConverter();
  BooleanConverter checkConv = new CheckBoxConverter();
  List selectionHandlers = new ArrayList();
  Dispatcher dispatcher = new DispatcherSupport();
  TitleProvider titleProvider = null;

  private static Logger logger = Logger.getLogger(SelectionMgr.class);

  SelectionModel selectionModel;

  public SelectionMgr(Dispatcher dispatcher, Form form) {
    this(dispatcher, form, new DefaultSelectionModel());
  }

  public SelectionMgr(Dispatcher dispatcher, Form form, SelectionModel selectionModel) {
    this.selectionModel = selectionModel;
    form.addFormListener(this);
    dispatcher.addRequestListener(null, null, this.dispatcher);
  }

  /**
   * must be called once before rendering
   */
  public void startRendering(RequestContext context) {
    selectionHandlers.clear();
    dispatcher.clear();
  }

  /**
   * must be called once after rendering
   */
  public void stopRendering() {
  }

  /**
   * if selection is enabled adds a checkbox or radiobutton element to the parent.
   */
  public void renderButton(Element parent, Object obj) {
    
    if (!selectionModel.isSelectable(obj)) {
      DomUtils.appendNbsp(parent);
      return;
    }

    int selMode = selectionModel.getMode();

    if (selMode == SelectionModel.SINGLE_SELECTION_HREF) {
      String id = DomUtils.randomId();
      parent.setAttribute("hrefId", id);
      dispatcher.addRequestListener(id, null, new HREFSelectHandler(obj));
      if (selectionModel.contains(obj))
        parent.setAttribute("style", "selected");
    }
    
    else if (selMode == SelectionModel.MULTIPLE_SELECTION_BUTTON) {
      String id = DomUtils.randomId();
      parent.setAttribute("buttonId", id);
      dispatcher.addRequestListener(id, null, new ButtonSelectHandler(obj));
      if (selectionModel.contains(obj))
        parent.setAttribute("selected", "true");
    }

    // create button element
    else if (selMode == SelectionModel.SINGLE_SELECTION
        || selMode == SelectionModel.MULTIPLE_SELECTION) {

      Element button;
      String buttonId = DomUtils.randomId();

      if (selectionModel.getMode() == SelectionModel.SINGLE_SELECTION) {
        button = RadioButton.addRadioButton(parent);
        RadioButton.setGroupId(button, groupId);
        RadioButton.setId(button, buttonId);
        selectionHandlers.add(new SelectionHandler(obj, button, radioConv));
      } else {
        button = CheckBox.addCheckBox(parent);
        CheckBox.setId(button, buttonId);
        selectionHandlers.add(new SelectionHandler(obj, button, checkConv));
      }

      Item.setId(button, DomUtils.randomId());
      Item.setSelected(button, selectionModel.contains(obj));
      if (titleProvider != null) {
        String title = titleProvider.getLabel(obj);
        button.setAttribute("title", title);
      }
    }
  }

  /**
   * Returns the model.
   * @return SelectionModel
   */
  public SelectionModel getSelectionModel() {
    return selectionModel;
  }

  /**
   * Sets the model.
   * @param model The model to set
   */
  public void setSelectionModel(SelectionModel selectionModel) {
    this.selectionModel = selectionModel;
  }

  class SelectionHandler {
    Object obj;
    Element elem;
    BooleanConverter conv;

    public SelectionHandler(Object obj, Element elem, BooleanConverter conv) {
      this.obj = obj;
      this.elem = elem;
      this.conv = conv;
    }

    public void validate(RequestContext context) {
      Map params = context.getParameters();
      switch (conv.isSelected(elem, params)) {
      case BooleanConverter.TRUE:
        if (selectionModel.getMode() == SelectionModel.SINGLE_SELECTION) {
          selectionModel.setSingleSelection(obj);
        } else {
          selectionModel.add(obj);
        }
        selectionModel.fireSelectionChanged(context);
        break;
      case BooleanConverter.FALSE:
        selectionModel.remove(obj);
        selectionModel.fireSelectionChanged(context);
        break;
      default: // UNKNOWN, i.e. not rendered
        break;
      }
    }
  }

  public void revert(RequestContext context) {
  }

  public boolean validate(RequestContext context) {
    logger.info("enter");
    for (Iterator it = selectionHandlers.iterator(); it.hasNext();) {
      SelectionHandler sh = (SelectionHandler) it.next();
      sh.validate(context);
    }
    return true;
  }

  /** single selection via href hyperlink */
  class HREFSelectHandler implements RequestListener {
    private Object node;

    HREFSelectHandler(Object node) {
      this.node = node;
    }

    public void request(RequestContext context) throws Exception {
      selectionModel.setSingleSelection(node);
      selectionModel.fireSelectionChanged(context);
    }
  }

  /** multiple selection via image buttons */
  class ButtonSelectHandler implements RequestListener {
    private Object node;

    ButtonSelectHandler(Object node) {
      this.node = node;
    }

    public void request(RequestContext context) throws Exception {
      if (selectionModel.contains(node))
        selectionModel.remove(node);
      else
        selectionModel.add(node);
      selectionModel.fireSelectionChanged(context);
    }
  }
  
  /**
   * if set creates title attribute
   */
  public TitleProvider getTitleProvider() {
    return titleProvider;
  }

  /**
   * if set creates title attribute
   */
  public void setTitleProvider(TitleProvider provider) {
    titleProvider = provider;
  }

}
