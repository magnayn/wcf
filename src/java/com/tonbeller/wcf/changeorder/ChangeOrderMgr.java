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
package com.tonbeller.wcf.changeorder;

import org.w3c.dom.Element;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.component.Form;
import com.tonbeller.wcf.component.RenderListener;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.scroller.Scroller;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * generates buttons to move objects in a list or tree
 * 
 * @author av
 */
public class ChangeOrderMgr implements RenderListener {
  Dispatcher dispatcher = new DispatcherSupport();
  ChangeOrderModel model;

  /** name of the DOM element */
  static final String BUTTON_ELEMENT = "move-button";
  /** attribute name of a BUTTON_ELEMENT_NAME element, value is STYLE_xxx */
  static final String STYLE_ATTR = "style";
  /** value for STYLE_ATTR */
  static final String STYLE_FWD = "fwd";
  /** value for STYLE_ATTR */
  static final String STYLE_BWD = "bwd";
  /** value for STYLE_ATTR */
  static final String STYLE_CUT = "cut";
  /** value for STYLE_ATTR */
  static final String STYLE_UNCUT = "uncut";
  /** value for STYLE_ATTR */
  static final String STYLE_PASTE_BEFORE = "paste-before";
  /** value for STYLE_ATTR */
  static final String STYLE_PASTE_AFTER = "paste-after";
  
  static final String TITLE_ATTR = "title";
  String titleFwd = "";
  String titleBwd = "";
  String titleCut = "";
  String titleUncut = "";
  String titlePasteBefore = "";
  String titlePasteAfter = "";

  Form form;

  public ChangeOrderMgr(Dispatcher parentDispatcher, Form form) {
    this.form = form;
    this.model = new DefaultChangeOrderModel();
    parentDispatcher.addRequestListener(null, null, dispatcher);
  }

  /**
   * creates new ChangeOrderMgr
   * @param parentDispatcher
   * @param form
   * @param model null or the model to use
   */
  public ChangeOrderMgr(Dispatcher parentDispatcher, Form form, ChangeOrderModel model) {
    this.form = form;
    this.model = model;
    parentDispatcher.addRequestListener(null, null, dispatcher);
  }

  /**
   * appends dom elements "move-button" with attribute "style=fwd|bwd" and "id",
   * e.g. &lt;move-button id="wcf.e5f8fbfc" style="bwd"/&gt;
   * <p />
   * There is one current ButtonRenderer which defines the state of the ChangeOrderMgr
   * 
   * @param elem the parent element to which the "move-button" element is appended as a child
   * @param scope defines the valid targets, where <code>node</code> may be moved. <code>node</code>
   * may be moved among other nodes that belong to the same scope. In a tree, the parent is a good choice 
   * to be the scope of its child nodes. 
   * @param nodeIndex the index of the the current <code>node</code> among its siblings
   * @param nodeCount number of nodes that belong to <code>scope</code>.
   */
  interface ButtonRenderer extends RenderListener {
    void renderButton(Element elem, Object scope, Object node, int nodeIndex, int nodeCount);
  }

  /**
   * creates "move forward" and "move backward" buttons. Allows the user to move a node
   * for one position with each click. This is easy for small amount of nodes, but requires
   * many clicks for larger amount.
   * 
   * @author av
   * @since 12.01.2005
   */
  class ForwardBackwardButtonRenderer implements ButtonRenderer {
    /** implement ButtonRenderer  */
    public void renderButton(Element elem, Object scope, Object node, int nodeIndex, int nodeCount) {
      if (nodeIndex == 0) {
        // first node can move forward only 
        // leave space for backward button only if there are inner nodes
        if (nodeCount > 2)
          appendEmptyButton(elem);
        appendMoveButton(elem, scope, node, STYLE_FWD, titleFwd, nodeIndex, nodeIndex + 1);
      } else if (nodeIndex == nodeCount - 1) {
        // last node can move backward only
        appendMoveButton(elem, scope, node, STYLE_BWD, titleBwd, nodeIndex, nodeIndex - 1);
        // leave space for forward button only if there are inner nodes
        if (nodeCount > 2)
          appendEmptyButton(elem);
      } else {
        // inner nodes can move forward and backward
        appendMoveButton(elem, scope, node, STYLE_BWD, titleBwd, nodeIndex, nodeIndex - 1);
        appendMoveButton(elem, scope, node, STYLE_FWD, titleFwd, nodeIndex, nodeIndex + 1);
      }
    }

    public void startRendering(RequestContext context) {
    }

    public void stopRendering() {
    }

    void appendEmptyButton(Element elem) {
      Element buttonElem = elem.getOwnerDocument().createElement(BUTTON_ELEMENT);
      elem.appendChild(buttonElem);
    }

    void appendMoveButton(Element elem, Object scope, Object item, String style, String title, int oldIndex,
        int newIndex) {
      Element buttonElem = elem.getOwnerDocument().createElement(BUTTON_ELEMENT);
      elem.appendChild(buttonElem);
      buttonElem.setAttribute(STYLE_ATTR, style);
      buttonElem.setAttribute(TITLE_ATTR, title);
      String id = DomUtils.randomId();
      buttonElem.setAttribute("id", id);
      dispatcher.addRequestListener(id, null, new ChangeOrderButtonHandler(scope, item, oldIndex,
          newIndex));
    }
  };

  /**
   * renders a button that allows the user to "cut" a node which means to mark it for move.
   * If the user "cut"s a node, the PasteButtonRenderer will become the current renderer to
   * render the paste buttons.
   * @author av
   * @since 12.01.2005
   */
  class CutButtonRenderer implements ButtonRenderer {
    public void renderButton(Element elem, Object scope, Object item, int nodeIndex, int nodeCount) {
      Element buttonElem = elem.getOwnerDocument().createElement(BUTTON_ELEMENT);
      elem.appendChild(buttonElem);
      buttonElem.setAttribute(STYLE_ATTR, STYLE_CUT);
      buttonElem.setAttribute(TITLE_ATTR, titleCut);
      String id = DomUtils.randomId();
      buttonElem.setAttribute("id", id);
      dispatcher.addRequestListener(id, null, new CutButtonHandler(scope, item, nodeIndex));
    }

    public void startRendering(RequestContext context) {
    }

    public void stopRendering() {
    }
  };

  /**
   * RequestHandler for a "Cut" button. Creates a PasteButtonRenderer for the selected node
   * and makes it the current buttonRenderer.
   * 
   * @author av
   * @since 12.01.2005
   */
  class CutButtonHandler implements RequestListener {
    Object scope;
    Object item;
    int nodeIndex;

    CutButtonHandler(Object scope, Object item, int nodeIndex) {
      this.scope = scope;
      this.item = item;
      this.nodeIndex = nodeIndex;
    }

    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      form.validate(context);
      if (model != null)
        buttonRenderer = new PasteButtonRenderer(scope, item, nodeIndex);
    }
  }

  /**
   * renders paste buttons. When rendering has finished (stopRendering() was called), the
   * current buttonRenderer is restored to a CutRenderer. So if the user collapses a tree node
   * instead of pasting a previously cutted node, the tree goes back into cut mode automatically.
   * 
   * @author av
   * @since 12.01.2005
   */
  class PasteButtonRenderer implements ButtonRenderer {
    Object scope;
    Object item;
    int nodeIndex;

    PasteButtonRenderer(Object scope, Object item, int nodeIndex) {
      this.scope = scope;
      this.item = item;
      this.nodeIndex = nodeIndex;
    }

    boolean equals(Object o1, Object o2) {
      if (o1 == null)
        return o2 == null;
      return o1.equals(o2);
    }

    public void renderButton(Element elem, Object scope, Object item, int nodeIndex, int nodeCount) {
      // may not move into another scope
      if (!equals(this.scope, scope))
        return;
      // current item gets an "undo cut" button
      Element buttonElem = elem.getOwnerDocument().createElement(BUTTON_ELEMENT);
      elem.appendChild(buttonElem);
      String id = DomUtils.randomId();
      buttonElem.setAttribute("id", id);
      if (this.nodeIndex == nodeIndex) {
        // create a "undo cut" button
        buttonElem.setAttribute(STYLE_ATTR, STYLE_UNCUT);
        buttonElem.setAttribute(TITLE_ATTR, titleUncut);
        dispatcher.addRequestListener(id, null, new UnCutButtonHandler());
      } else if (this.nodeIndex < nodeIndex) {
        // create a "paste after" button
        buttonElem.setAttribute(STYLE_ATTR, STYLE_PASTE_AFTER);
        buttonElem.setAttribute(TITLE_ATTR, titlePasteAfter);
        dispatcher.addRequestListener(id, null, new ChangeOrderButtonHandler(scope, item,
            this.nodeIndex, nodeIndex));
      } else {
        // create a "paste before" button
        buttonElem.setAttribute(STYLE_ATTR, STYLE_PASTE_BEFORE);
        buttonElem.setAttribute(TITLE_ATTR, titlePasteBefore);
        dispatcher.addRequestListener(id, null, new ChangeOrderButtonHandler(scope, item,
            this.nodeIndex, nodeIndex));
      }
    }

    public void startRendering(RequestContext context) {
    }

    public void stopRendering() {
      buttonRenderer = new CutButtonRenderer();
    }
  };

  /**
   * handler for the selected node in "paste mode". If the user clicks on that node again,
   * "paste mode" will be reset to "cut mode".
   * @author av
   * @since 12.01.2005
   */
  class UnCutButtonHandler implements RequestListener {
    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      form.validate(context);
      buttonRenderer = new CutButtonRenderer();
    }
  }

  /**
   * button handler that moves a node from one position to another. Used in forward/backward mode as well
   * as in paste mode.
   * @author av
   * @since 12.01.2005
   */
  class ChangeOrderButtonHandler implements RequestListener {
    Object scope;
    Object item;
    int oldIndex;
    int newIndex;

    ChangeOrderButtonHandler(Object scope, Object item, int oldIndex, int newIndex) {
      this.scope = scope;
      this.item = item;
      this.oldIndex = oldIndex;
      this.newIndex = newIndex;
    }

    public void request(RequestContext context) throws Exception {
      Scroller.enableScroller(context);
      form.validate(context);
      if (model != null)
        model.move(scope, item, oldIndex, newIndex);
    }
  }
  
  /**
   * the current buttonRenderer, implements some kind of state machine.
   */
  ButtonRenderer buttonRenderer = new ForwardBackwardButtonRenderer();

  public void renderButton(Element elem, Object scope, Object node, int nodeIndex, int nodeCount) {
    if (model == null || !model.mayMove(scope, node))
      return;

    // a single node can not be moved around
    if (nodeCount < 2)
      return;

    buttonRenderer.renderButton(elem, scope, node, nodeIndex, nodeCount);
  }


  /**
   * called once before rendering of buttons starts
   */
  public void startRendering(RequestContext context) {
    Resources res = context.getResources(ChangeOrderMgr.class);
    titleFwd = res.getString("ChangeOrderMgr.titleFwd");
    titleBwd = res.getString("ChangeOrderMgr.titleBwd");
    titleCut = res.getString("ChangeOrderMgr.titleCut");
    titleUncut = res.getString("ChangeOrderMgr.titleUncut");
    titlePasteBefore = res.getString("ChangeOrderMgr.titlePasteBefore");
    titlePasteAfter = res.getString("ChangeOrderMgr.titlePasteAfter");
    
    dispatcher.clear();
    buttonRenderer.startRendering(context);
  }

  /**
   * called once after rendering of buttons has finished
   */
  public void stopRendering() {
    buttonRenderer.stopRendering();
  }

  public ChangeOrderModel getModel() {
    return model;
  }

  public void setModel(ChangeOrderModel model) {
    this.model = model;
  }

  public void setCutPasteMode(boolean b) {
    if (b)
      buttonRenderer = new CutButtonRenderer();
    else
      buttonRenderer = new ForwardBackwardButtonRenderer();
  }

}