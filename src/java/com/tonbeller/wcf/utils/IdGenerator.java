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
 * Created on 14.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.tonbeller.wcf.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class IdGenerator {
  int counter = 0;
  public void generate(Node root, String prefix) {
    if (root.getNodeType() == Node.ELEMENT_NODE) {
      Element e = (Element) root;
      String id = e.getAttribute("id");
      if (id == null || id.length() == 0) {
        id = prefix + counter;
        e.setAttribute("id", id);
        counter += 1;
      } else if (id.startsWith("$id.")) {
        id = prefix + id.substring(4);
        e.setAttribute("id", id);
      }
    }
    NodeList list = root.getChildNodes();
    int N = list.getLength();
    for (int i = 0; i < N; i++)
      generate(list.item(i), prefix);
  }
}