/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.component.Renderable;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.utils.DomUtils;
import com.tonbeller.wcf.utils.XmlUtils;

public class PopUp implements Renderable {
  private String label;
  private String image;
  private String id = DomUtils.randomId();

  private List items = new ArrayList();

  public PopUp() {
  }

  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * simplified visitor
   */
  private abstract class Switch {
    abstract void handleGroupItem(GroupItem g);

    abstract void handleMenuItem(MenuItem m);

    void handleItem(Item it) {
      if (it == null)
        return;
      if (it instanceof GroupItem)
        handleGroupItem((GroupItem) it);
      else
        handleMenuItem((MenuItem) it);
    }

    void handleList(List list) {
      for (Iterator it = list.iterator(); it.hasNext();) {
        handleItem((Item) it.next());
      }
    }

  }

  private class EmptySwitch extends Switch {
    boolean empty = true;

    void handleGroupItem(GroupItem g) {
      handleList(g.getChildren());
    }

    void handleMenuItem(MenuItem m) {
      empty = false;
    }

    public boolean isEmpty() {
      return empty;
    }
  }

  private boolean isEmpty(List list) {
    EmptySwitch es = new EmptySwitch();
    es.handleList(list);
    return es.isEmpty();
  }

  private class Renderer extends Switch {
    private Element parent;
    private int level;

    Renderer(Element parent, int level) {
      this.parent = parent;
      this.level = level;
    }

    void handleGroupItem(GroupItem item) {
      if (isEmpty(item.getChildren()))
        return;
      Element elem = DomUtils.appendElement(parent, "popup-group");
      setItemAttrs(elem, item);
      Renderer r = new Renderer(elem, level + 1);
      r.handleList(item.getChildren());
    }

    void handleMenuItem(MenuItem item) {
      Element elem = DomUtils.appendElement(parent, "popup-item");
      setItemAttrs(elem, item);
      elem.setAttribute("href", item.getHref());
    }

    private void setItemAttrs(Element elem, Item item) {
      elem.setAttribute("level", "" + level);
      if (item.getLabel() != null)
        elem.setAttribute("label", item.getLabel());
      if (item.getImage() != null)
        elem.setAttribute("image", item.getImage());
    }
  }

  public Element render(Document factory) {
    if (isEmpty(items))
      return null;
    Element popup = factory.createElement("popup-menu");
    popup.setAttribute("id", id);
    if (label != null)
      popup.setAttribute("label", label);
    if (image != null)
      popup.setAttribute("image", image);
    Renderer r = new Renderer(popup, 0);
    r.handleList(items);
    return popup;
  }

  public Document render(RequestContext context) throws Exception {
    Document dom = XmlUtils.createDocument();
    Element root = render(dom);
    dom.appendChild(root);
    return dom;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
