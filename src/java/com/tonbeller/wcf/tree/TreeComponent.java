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

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.changeorder.ChangeOrderMgr;
import com.tonbeller.wcf.changeorder.ChangeOrderModel;
import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.NestableComponentSupport;
import com.tonbeller.wcf.component.RenderListener;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.convert.BooleanConverter;
import com.tonbeller.wcf.convert.CheckBoxConverter;
import com.tonbeller.wcf.convert.RadioButtonConverter;
import com.tonbeller.wcf.scroller.Scroller;
import com.tonbeller.wcf.selection.SelectionMgr;
import com.tonbeller.wcf.selection.SelectionModel;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * Tree Component
 * 
 * @author av
 */
public class TreeComponent extends NestableComponentSupport {

  String treeElementName = "xtree-component";
  NodeRenderer nodeRenderer = new DefaultNodeRenderer();
  SelectionMgr selectionMgr;
  ChangeOrderMgr changeOrderMgr;
  ChangeOrderModel changeOrderModel;
  String border = null;
  String renderId = null;
  String width = null;
  String error = null;

  Set expanded = new HashSet();
  Dispatcher dispatcher = new DispatcherSupport();

  BooleanConverter radioConv = new RadioButtonConverter();
  BooleanConverter checkConv = new CheckBoxConverter();

  // initialized once in initialize()
  TreeModel model;
  TreeBounding bounding = NO_BOUNDING;
  private static final TreeBounding NO_BOUNDING = new TreeBounding() {
    public boolean isBounded(Object parent) {
      return false;
    }
    public void unbound(Object parent) {
    }
  };

  // initialized for every render() call
  RequestContext context;
  Document factory;
  int nodeCounter;

  DeleteNodeModel deleteNodeModel;

  TreeModelChangeListener changeListener = new TreeModelChangeListener() {
    public void treeModelChanged(TreeModelChangeEvent event) {
      if (event.isIdentityChanged()) {
        selectionMgr.getSelectionModel().clear();
        expanded.clear();
        dispatcher.clear();
      }
    }
  };

  /**
   * Constructor for TreeComponent.
   */
  public TreeComponent(String id, Component parent) {
    this(id, parent, null);
  }

  /**
   * Constructor for TreeComponent.
   */
  public TreeComponent(String id, Component parent, TreeModel model) {
    super(id, parent);
    selectionMgr = new SelectionMgr(getDispatcher(), this);
    changeOrderMgr = new ChangeOrderMgr(getDispatcher(), this);
    super.getDispatcher().addRequestListener(null, null, dispatcher);

    setModel(model);
  }

  /**
   * comparator fuer die expanded nodes
   */
  public void setComparator(Comparator comp) {
    expanded = new TreeSet(comp);
  }

  /**
   * set the model to use
   * @param model null or a TreeModel. If the TreeModel implements the ChangeOrderModel,
   * its used by the ChangeOrderMgr.
   */
  public void setModel(TreeModel newModel) {
    bounding = NO_BOUNDING;
    if (model != null)
      model.removeTreeModelChangeListener(changeListener);
    this.model = newModel;
    if (model != null)
      model.addTreeModelChangeListener(changeListener);
    if (model instanceof ChangeOrderModel)
      changeOrderMgr.setModel((ChangeOrderModel) model);
    else
      changeOrderMgr.setModel(null);
    expanded.clear();
    selectionMgr.getSelectionModel().clear();
    dispatcher.clear();
  }

  public Element render(RequestContext context, Document factory) throws Exception {
    return render(context, factory, true);
  }

  /**
   * renders the component
   * @param context the wcf context of the current request
   * @param clearDispatcher clears the dispatcher so all buttons/hyperlinks from
   * the previous render() call become invalid. Then renders the component.
   */
  private Element render(RequestContext context, Document factory, boolean clearDispatcher)
    throws Exception {
    this.factory = factory;
    this.context = context;
    if (clearDispatcher)
      dispatcher.clear();

    Element treeElem = factory.createElement(treeElementName);
    renderTree(treeElem);
    return treeElem;
  }

  Element renderTree(Element treeElem) {
    selectionMgr.startRendering(context);
    changeOrderMgr.startRendering(context);
    if (nodeRenderer instanceof RenderListener)
       ((RenderListener) nodeRenderer).startRendering(context);
    nodeCounter = 0;
    Object[] roots = model.getRoots();
    for (int i = 0; i < roots.length; i++) {
      Element nodeElem = renderNode(null, roots, i, 0);
      if (nodeElem != null)
        treeElem.appendChild(nodeElem);
    }
    if (nodeRenderer instanceof RenderListener)
       ((RenderListener) nodeRenderer).stopRendering();
    changeOrderMgr.stopRendering();
    selectionMgr.stopRendering();
    if (border != null)
      treeElem.setAttribute("border", border);
    if (renderId != null)
      treeElem.setAttribute("renderId", renderId);
    if (width != null)
      treeElem.setAttribute("width", width);
    if (error != null)
      treeElem.setAttribute("error", error);
    return treeElem;
  }

  /**
   * returns the Element for nodes[nodeIndex] or null, if this
   * node was deleted
   */
  Element renderNode(Object parent, Object[] nodes, int nodeIndex, int level) {
    Object node = nodes[nodeIndex];
    if (deleteNodeModel != null && deleteNodeModel.getDeleted().contains(node))
      return null;

    Element nodeElem = nodeRenderer.renderNode(context, factory, node);
    ++nodeCounter;
    // even / odd row count
    renderEvenOddAttr(nodeElem);
    String id = DomUtils.randomId();
    nodeElem.setAttribute("id", id);
    nodeElem.setAttribute("level", "" + level);
    renderExpandedAttr(node, nodeElem, id);
    // recurse
    if (expanded.contains(node)) {
      // node is expanded, so render its children
      Object[] children = model.getChildren(node);
      for (int i = 0; i < children.length; i++) {
        Element childElem = renderNode(node, children, i, level + 1);
        if (childElem != null)
          nodeElem.appendChild(childElem);
      }
    }
    // render selection
    selectionMgr.renderButton(nodeElem, node);
    changeOrderMgr.renderButton(nodeElem, parent, node, nodeIndex, nodes.length);
    renderDeleteButton(nodeElem, node);
    return nodeElem;
  }

  void renderDeleteButton(Element elem, final Object node) {
    if (deleteNodeModel == null || !deleteNodeModel.isDeletable(node))
      return;
    Element button = DomUtils.appendElement(elem, "delete-button");
    String id = DomUtils.randomId();
    button.setAttribute("id", id);
    RequestListener deleter = new RequestListener() {
      public void request(RequestContext context) throws Exception {
        Scroller.enableScroller(context);
        // recursively delete node and all of its children
        NodeIterator it = new NodeIterator(getModel(), node, false);
        while (it.hasNext()) {
          Object n = it.next();
          deleteNodeModel.delete(n);
          getSelectionModel().remove(n);
          expanded.remove(n);
        }
      }
    };
    dispatcher.addRequestListener(id, null, deleter);
  }

  void renderExpandedAttr(Object node, Element nodeElem, String id) {
    if (bounding.isBounded(node)) {
      nodeElem.setAttribute("state", "bounded");
      dispatcher.addRequestListener(id + ".unbound", null, new UnboundHandler(node));
    } else if (expanded.contains(node)) {
      nodeElem.setAttribute("state", "expanded");
      dispatcher.addRequestListener(id + ".collapse", null, new CollapseHandler(node));
    } else if (model.hasChildren(node)) {
      nodeElem.setAttribute("state", "collapsed");
      dispatcher.addRequestListener(id + ".expand", null, new ExpandHandler(node));
    } else {
      nodeElem.setAttribute("state", "leaf");
    }
  }

  void renderEvenOddAttr(Element nodeElem) {
    if (nodeCounter % 2 == 0)
      nodeElem.setAttribute("style", "even");
    else
      nodeElem.setAttribute("style", "odd");
  }

  class CollapseHandler implements RequestListener {
    Object node;
    public CollapseHandler(Object node) {
      this.node = node;
    }
    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      expanded.remove(node);
      validate(context);
    }
  }

  class ExpandHandler implements RequestListener {
    Object node;
    public ExpandHandler(Object node) {
      this.node = node;
    }
    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      expanded.add(node);
      validate(context);
    }
  }

  class UnboundHandler implements RequestListener {
    Object node;
    public UnboundHandler(Object node) {
      this.node = node;
    }
    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      bounding.unbound(node);
      validate(context);
    }
  }
  
  /**
   * returns the current selection
   */
  public SelectionModel getSelectionModel() {
    return selectionMgr.getSelectionModel();
  }

  /**
   * changes the selection. Must call initialize() first.
   */
  public void setSelectionModel(SelectionModel selectionModel) {
    selectionMgr.setSelectionModel(selectionModel);
  }

  /**
   * collapses a node
   */
  public void collapse(Object node) {
    expanded.remove(node);
  }

  /**
   * expands a node
   */
  public void expand(Object node) {
    expanded.add(node);
  }

  /**
   * collapses all nodes
   */
  public void collapseAll() {
    expanded.clear();
  }

  /**
   * expands the parents of the selected nodes. So all selected nodes will be visible
   * @deprecated use expandSelected(boolean) instead 
   */
  public void expandSelected() {
    expandSelected(false);
  }

  /**
   * expands the parents of the selected nodes. So all selected nodes will be visible 
   * @param expandSelected true, if the selected nodes should be expanded too (in case they have children)
   */
  public void expandSelected(boolean expandSelected) {
    expanded.clear();
    for (Iterator it = selectionMgr.getSelectionModel().getSelection().iterator(); it.hasNext();) {
      Object node = it.next();
      if (expandSelected && model.hasChildren(node))
        expanded.add(node);
      node = model.getParent(node);
      while (node != null) {
        expanded.add(node);
        node = model.getParent(node);
      }
    }
  }

  /**
   * sets the selection to the currently visible nodes
   */
  public void selectVisible() {
    SelectionModel sm = selectionMgr.getSelectionModel();
    sm.clear();
    Object[] roots = model.getRoots();
    for (int i = 0; i < roots.length; i++)
      recurseSelectVisible(sm, roots[i]);
  }

  private void recurseSelectVisible(SelectionModel sm, Object node) {
    if (sm.isSelectable(node))
      sm.add(node);
    if (expanded.contains(node)) {
      Object[] children = model.getChildren(node);
      for (int i = 0; i < children.length; i++)
        recurseSelectVisible(sm, children[i]);
    }
  }

  /**
   * Returns the model.
   * @return TreeModel
   */
  public TreeModel getModel() {
    return model;
  }

  /**
   * Returns the nodeRenderer.
   * @return NodeRenderer
   */
  public NodeRenderer getNodeRenderer() {
    return nodeRenderer;
  }

  /**
   * Returns the treeElementName.
   * @return String
   */
  public String getTreeElementName() {
    return treeElementName;
  }

  /**
   * Sets the nodeRenderer. If NodeRenderer is a RequestListener it will
   * be registered as a default listener (i.e. receives all requests).
   * <p> 
   * If NodeRenderer is a RenderListener, it will be informed when rendering
   * starts and stops (e.g. to clear RequestListeners that corresponds to buttons)
   * 
   * @param nodeRenderer The nodeRenderer to set
   * @see RequestListeningNodeRenderer
   */
  public void setNodeRenderer(NodeRenderer nodeRenderer) {
    if (this.nodeRenderer instanceof RequestListener)
      super.getDispatcher().removeRequestListener((RequestListener) this.nodeRenderer);
    this.nodeRenderer = nodeRenderer;
    if (this.nodeRenderer instanceof RequestListener)
      super.getDispatcher().addRequestListener(null, null, (RequestListener) this.nodeRenderer);
  }

  /**
   * Sets the treeElementName.
   * @param treeElementName The treeElementName to set
   */
  public void setTreeElementName(String treeElementName) {
    this.treeElementName = treeElementName;
  }

  /**
   * Returns the changeOrderModel.
   * @return ChangeOrderModel
   */
  public ChangeOrderModel getChangeOrderModel() {
    return changeOrderMgr.getModel();
  }

  /**
   * Sets the changeOrderModel.
   * @param changeOrderModel The changeOrderModel to set
   */
  public void setChangeOrderModel(ChangeOrderModel changeOrderModel) {
    changeOrderMgr.setModel(changeOrderModel);
  }

  /**
   * sets the UI style for moving nodes (for TreeModels that implement ChangeOrderModel)
   * @see com.tonbeller.wcf.changeorder.ChangeOrderModel
   */
  public void setCutPasteMode(boolean b) {
    changeOrderMgr.setCutPasteMode(b);
  }

  /**
   * gets the border attribute of the generated table. 
   * Overrides the global stylesheet parameter "border". 
   * @return the border attribute or null
   */
  public String getBorder() {
    return border;
  }

  /**
   * sets the border attribute of the generated table. 
   * Overrides the global stylesheet parameter "border". 
   * @param border the border attribute or null to use the stylesheet parameter
   */
  public void setBorder(String border) {
    this.border = border;
  }

  /**
   * sets the renderId attribute of the generated table. 
   * Overrides the global stylesheet parameter "renderId". 
   * @param renderId the renderId attribute or null to use the stylesheet parameter
   */
  public void setRenderId(String renderId) {
    this.renderId = renderId;
  }

  /**
   * gets the renderId attribute of the generated table. 
   * Overrides the global stylesheet parameter "renderId". 
   * @return the renderId attribute or null
   */
  public String getRenderId() {
    return renderId;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String string) {
    width = string;
  }

  public DeleteNodeModel getDeleteNodeModel() {
    return deleteNodeModel;
  }

  public void setDeleteNodeModel(DeleteNodeModel model) {
    deleteNodeModel = model;
  }

  public String getError() {
    return error;
  }

  public void setError(String string) {
    error = string;
  }

  public TreeBounding getBounding() {
    return bounding;
  }

  public void setBounding(TreeBounding bounding) {
    if (bounding == null)
      bounding = NO_BOUNDING;
    this.bounding = bounding;
  }
}
