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
package com.tonbeller.wcf.catedit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.changeorder.ChangeOrderMgr;
import com.tonbeller.wcf.changeorder.ChangeOrderModel;
import com.tonbeller.wcf.changeorder.ChangeOrderUtils;
import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.NestableComponentSupport;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.scroller.Scroller;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * The editor component. Creates a DOM that shows cat-item elements inside 
 * cat-category elements.
 * 
 * @author av
 */
public class CategoryEditor extends NestableComponentSupport implements ChangeOrderModel, CategoryModelChangeListener {
  CategoryModel model;
  String rootElementName = "cat-edit";
  String changeCategoryButtonElementName = "cat-button";
  CategoryElementRenderer categoryRenderer = new DefaultCategoryElementRenderer();
  ItemElementRenderer itemRenderer = new DefaultItemElementRenderer();
  Dispatcher dispatcher = new DispatcherSupport();

  ChangeOrderMgr changeOrderMgr;

  Document factory;
  RequestContext context;

  public CategoryEditor(String id, Component parent) {
    super(id, parent);
    super.getDispatcher().addRequestListener(null, null, dispatcher);
  }

  public CategoryEditor(String id, Component parent, CategoryModel model) {
    super(id, parent);
    super.getDispatcher().addRequestListener(null, null, dispatcher);
    setModel(model);
  }

  public void initialize(RequestContext context) throws Exception {
    super.initialize(context);
    // super.getDispatcher() is not cleared
    changeOrderMgr = new ChangeOrderMgr(super.getDispatcher(), this, this);
  }

  public Element render(RequestContext context, Document factory) throws Exception {
    this.factory = factory;
    this.context = context;

    itemRenderer.startRendering(context);
      
    dispatcher.clear();
    dispatcher.addRequestListener(null, null, itemRenderer);

    Element rootElem = factory.createElement(rootElementName);
    List categories = model.getCategories();
    if (categories != null && categories.size() > 0)
      renderRootElement(rootElem);

    itemRenderer.stopRendering();

    return rootElem;
  }

  void renderRootElement(Element rootElem) {
    changeOrderMgr.startRendering(context);

    for (Iterator it = model.getCategories().iterator(); it.hasNext();) {
      Category cat = (Category) it.next();
      Element catElem = renderCategory(cat);
      rootElem.appendChild(catElem);
    }

    changeOrderMgr.stopRendering();
  }

  Element renderCategory(Category cat) {
    Element catElem = categoryRenderer.render(context, factory, cat);
    int count = cat.getItems().size();
    int index = 0;
    for (Iterator it = cat.getItems().iterator(); it.hasNext();) {
      Item item = (Item) it.next();
      Element itemElem = renderItem(item, cat);
      catElem.appendChild(itemElem);
      changeOrderMgr.renderButton(itemElem, cat, item, index, count);
      ++index;
    }
    return catElem;
  }

  Element renderItem(Item item, Category cat) {
    Element itemElem = itemRenderer.render(context, factory, cat, item);
    int N = cat.getItems().size();

    // add buttons to move this item to the other categories
    if (item.isMovable() && (N > 1 || cat.isEmptyAllowed())) {
      for (Iterator it = model.getCategories().iterator(); it.hasNext();) {
        Category other = (Category) it.next();
        if (other.equals(cat)) // dont move to the current category
          continue;
        Element buttonElem = renderChangeCategoryButton(item, cat, other);
        itemElem.appendChild(buttonElem);
      }
    }
    return itemElem;
  }

  Element renderChangeCategoryButton(Item item, Category source, Category target) {
    Element buttonElem = factory.createElement(changeCategoryButtonElementName);
    buttonElem.setAttribute("icon", target.getIcon());
    String id = DomUtils.randomId();
    buttonElem.setAttribute("id", id);
    dispatcher.addRequestListener(id, null, new ChangeCategoryButtonHandler(item, source, target));
    return buttonElem;
  }

  class ChangeCategoryButtonHandler implements RequestListener {
    Item item;
    Category source;
    Category target;
    ChangeCategoryButtonHandler(Item item, Category source, Category target) {
      this.item = item;
      this.source = source;
      this.target = target;
    }
    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      validate(context);
      source.removeItem(item);
      target.addItem(item);
    }
  }

  /**
   * Returns the model.
   * @return CategoryModel
   */
  public CategoryModel getModel() {
    return model;
  }

  /**
   * Sets the model.
   * @param model The model to set
   */
  public void setModel(CategoryModel newModel) {
    if (model != null)
      model.removeCategoryModelChangeListener(this);
    model = newModel;
    if (model != null)
      model.addCategoryModelChangeListener(this);
  }

  /**
   * invalidates hyperlinks
   * @param event
   */
  public void categoryModelChanged(CategoryModelChangeEvent event) {
    itemRenderer.categoryModelChanged(event);
    dispatcher.clear();
  }

  /**
   * Returns the categoryRenderer.
   * @return CategoryElementRenderer
   */
  public CategoryElementRenderer getCategoryRenderer() {
    return categoryRenderer;
  }

  /**
   * Returns the changeCategoryButtonElementName.
   * @return String
   */
  public String getChangeCategoryButtonElementName() {
    return changeCategoryButtonElementName;
  }

  /**
   * Returns the itemRenderer.
   * @return ItemElementRenderer
   */
  public ItemElementRenderer getItemRenderer() {
    return itemRenderer;
  }

  /**
   * Returns the rootElementName.
   * @return String
   */
  public String getRootElementName() {
    return rootElementName;
  }

  /**
   * Sets the categoryRenderer.
   * @param categoryRenderer The categoryRenderer to set
   */
  public void setCategoryRenderer(CategoryElementRenderer categoryRenderer) {
    this.categoryRenderer = categoryRenderer;
  }

  /**
   * Sets the changeCategoryButtonElementName.
   * @param changeCategoryButtonElementName The changeCategoryButtonElementName to set
   */
  public void setChangeCategoryButtonElementName(String changeCategoryButtonElementName) {
    this.changeCategoryButtonElementName = changeCategoryButtonElementName;
  }

  /**
   * Sets the itemRenderer.
   * @param itemRenderer The itemRenderer to set
   */
  public void setItemRenderer(ItemElementRenderer itemRenderer) {
    this.itemRenderer = itemRenderer;
  }

  /**
   * Sets the rootElementName.
   * @param rootElementName The rootElementName to set
   */
  public void setRootElementName(String rootElementName) {
    this.rootElementName = rootElementName;
  }

  /**
   * @see com.tonbeller.wcf.changeorder.ChangeOrderModel#mayMove(Object)
   */
  public boolean mayMove(Object scope, Object item) {
    Category cat = (Category) scope;
    if (!cat.isOrderSignificant())
      return false;
    return cat.getItems().size() > 0 || cat.isEmptyAllowed();
  }

  /**
   * implement ChangeOrderModel
   */
  public void move(Object scope, Object item, int oldIndex, int newIndex) {
    Category cat = (Category) scope;
    // browser back button etc
    if (cat == null)
      return;
    // change element order in a copy (why?)
    List items = new ArrayList();
    items.addAll(cat.getItems());
    ChangeOrderUtils.move(items, oldIndex, newIndex);
    // save changes
    cat.changeOrder(items);
  }

}
