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
package com.tonbeller.wcf.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DomUtils {

  /**
   * returns Vector of all Child elements, specified by tag name.
   * same as Element.getElementsByTagName, but not recursive.
   */
  public static List getChildElemsByTagName(Element parent, String tagName) {
    ArrayList retVec = new ArrayList();

    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); ++i) {
      if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
        Element child = (Element) children.item(i);
        if (child.getTagName().equals(tagName))
          retVec.add(child);
      }
    }
    return retVec;
  }

  /**
   * returns List of all direct child elements
   */
  public static List getChildElements(Node parent) {
    ArrayList retVec = new ArrayList();

    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); ++i) {
      if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
        retVec.add(children.item(i));
      }
    }
    return retVec;
  }

  /**
   * returns List of all direct child elements exept Attribute nodes.
   * Among others this will contain Element nodes and Text children.
   */
  public static List getChildNodesExceptAttributes(Node parent) {
    ArrayList retVec = new ArrayList();
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); ++i) {
      if (children.item(i).getNodeType() == Node.TEXT_NODE
          || children.item(i).getNodeType() == Node.ELEMENT_NODE) {
        retVec.add(children.item(i));
      }
    }
    return retVec;
  }

  /**
   * removes all children of element type
   */
  public static void removeChildElements(Element parent) {
    List v = getChildElements(parent);
    Iterator en = v.iterator();
    while (en.hasNext())
      parent.removeChild((Node) en.next());
  }

  /**
   * removes all children of element ant text type
   */
  public static void removeChildNodesExceptAttributes(Element parent) {
    List v = getChildNodesExceptAttributes(parent);
    Iterator en = v.iterator();
    while (en.hasNext())
      parent.removeChild((Node) en.next());
  }

  /** removes all element/text children, then adds a new single text child */
  public static Text setText(Element parent, String text) {
    removeChildNodesExceptAttributes(parent);
    Document doc = parent.getOwnerDocument();
    Text child = doc.createTextNode(text);
    parent.appendChild(child);
    return child;
  }

  /**
   * returns first child element with fitting tag name.
   */
  public static Element getChildElemByTagName(Element parent, String tagName) {
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); ++i) {
      if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
        Element child = (Element) children.item(i);
        if (child.getTagName().equals(tagName))
          return child;
      }
    }
    return null;
  }

  /**
   * returns first child element with fitting id.
   */
  public static Element getChildElemById(Element parent, String id) {
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); ++i) {
      if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
        Element child = (Element) children.item(i);
        if (XoplonNS.getAttribute(child, "id").equals(id))
          return child;
      }
    }
    return null;
  }

  /**
   * returns the Document. node itself may be a Document node in which case
   * node.getOwnerDocument() will return null
   */
  public static Document getDocument(Node node) {
    if (node.getNodeType() == Node.DOCUMENT_NODE)
      return (Document) node;
    return node.getOwnerDocument();
  }

  /** creates a new element of type and appends it to parent */
  public static Element appendElement(Element parent, String type) {
    Document doc = parent.getOwnerDocument();
    Element elem = doc.createElement(type);
    parent.appendChild(elem);
    return elem;
  }

  public static Text appendNbsp(Element parent) {
    return appendText(parent, "\u00a0");
  }

  public static Text appendText(Element parent, String text) {
    Document doc = parent.getOwnerDocument();
    Text x = doc.createTextNode(text);
    parent.appendChild(x);
    return x;
  }

  static final Random random = new Random();

  public static synchronized String randomId() {
    return "wcf" + Integer.toHexString(random.nextInt());
  }

  public static synchronized void setRandomSeed(long seed) {
    random.setSeed(seed);
  }

  /**
   * adds an attribute with random value to all elements, that do not
   * have an attribute with the specified name.
   * @param root the root element
   * @param attrName the name of the attribute, e.g. "id"
   */
  public static void generateIds(Node root, String attrName) {
    if (root.getNodeType() == Node.ELEMENT_NODE) {
      Element e = (Element) root;
      String id = e.getAttribute(attrName);
      if (id == null || id.length() == 0)
        e.setAttribute(attrName, randomId());
    }
    NodeList list = root.getChildNodes();
    int N = list.getLength();
    for (int i = 0; i < N; i++)
      generateIds(list.item(i), attrName);
  }

  public static void generateIds(Node root) {
    generateIds(root, "id");
  }

  /**
   * fast search for Element with id attribute
   * @param root the root Element of the search. search will cover root and 
   * its descendants
   * @param id the id to search for
   * @return null or the element
   */
  public static Element findElementWithId(String id, Element root) {
    if (id.equals(root.getAttribute("id")))
      return root;
    NodeList list = root.getChildNodes();
    int len = list.getLength();
    for (int i = 0; i < len; i++) {
      Node n = list.item(i);
      if (n.getNodeType() != Node.ELEMENT_NODE)
        continue;
      Element child = (Element) list.item(i);
      Element found = findElementWithId(id, child);
      if (found != null)
        return found;
    }
    return null;
  }

  /**
   * Oracle 10i does not allow to remove an attribute that does
   * not exist. This method will silently ignore any exceptions
   * that occur while removing the attribute.
   */
  public static void removeAttribute(Element elem, String name) {
    try {
      elem.removeAttribute(name);
    } catch (Exception e) {
      // ignore
    }
  }
}
