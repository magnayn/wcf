package com.tonbeller.tbutils.res;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Adapter fuer ResourceBundle
 */
public class BundleResourceProvider implements ResourceProvider {
  private ResourceBundle resb;

  BundleResourceProvider(ResourceBundle resb) {
    this.resb = resb;
  }

  public String getString(String key) {
    try {
      return resb.getString(key);
    } catch (MissingResourceException e) {
      return null;
    }
  }

  public Collection keySet() {
    Set set = new HashSet();
    for (Enumeration en = resb.getKeys(); en.hasMoreElements();)
      set.add(en.nextElement());
    return set;
  }

  public void close() {
  }

}