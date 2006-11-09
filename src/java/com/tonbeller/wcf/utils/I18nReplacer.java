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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * replaces attribute values in a DOM tree with values from a resource bundle
 * Attribute value must start with "fmt:", e.g. "fmt:akey" will replace
 * the string "fmt:akey" with the value of found for "akey" in the resource
 * bundle.
 * 
 * @author av
 */
public abstract class I18nReplacer {
  
  public static final String PREFIX = "fmt:";
  
  public static I18nReplacer instance(final ResourceBundle resb) {
    return new I18nReplacer() {
      protected String internalReplace(String key) {
        try {
          return resb.getString(key);
        } catch (MissingResourceException e) {
          return "???" + key + "???";
        }
      }
      
    };
  }

  public static I18nReplacer instance(final com.tonbeller.tbutils.res.Resources res) {
    return new I18nReplacer() {
      protected String internalReplace(String key) {
        try {
          return res.getString(key);
        } catch (MissingResourceException e) {
          return "???" + key + "???";
        }
      }
    };
  }
  
  protected abstract String internalReplace(String key);
  
  public String replace(String value) {
    if (value.startsWith(PREFIX)) {
      return internalReplace(value.substring(4));
    }
    return value;
  }
  
  public void replaceAll(Node root) {
    if (root.getNodeType() == Node.ELEMENT_NODE) {
      Element e = (Element) root;
      NamedNodeMap atts = e.getAttributes();
      final int N = atts.getLength();
      for (int i = 0; i < N; i++) {
        Attr attr = (Attr) atts.item(i);
        String value = attr.getValue();
        if (value.startsWith(PREFIX)) {
          value = internalReplace(value.substring(4));
          attr.setValue(value);
        }
      }
    }
    if (root.getNodeType() == Node.TEXT_NODE) {
      Text text = (Text) root;
      String data = text.getData();
      if (data.startsWith(PREFIX)) {
        data = internalReplace(data.substring(4));
        text.setData(data);
      }
    }
    NodeList list = root.getChildNodes();
    int N = list.getLength();
    for (int i = 0; i < N; i++)
      replaceAll(list.item(i));
  }
}
