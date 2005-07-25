package com.tonbeller.tbutils.res;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * Adapter for ServletContext initParameter
 * @author av
 */
public class ServletContextResourceProvider implements ResourceProvider {
  private ServletContext sc;

  public ServletContextResourceProvider(ServletContext sc) {
    this.sc = sc;
  }

  public String getString(String key) {
    return sc.getInitParameter(key);
  }

  public Collection keySet() {
    Set set = new HashSet();
    for (Enumeration en = sc.getInitParameterNames(); en.hasMoreElements();)
      set.add(en.nextElement());
    return set;
  }

  public void close() {
  }
  public void dump(Dumper d) {
    d.dump(this);
  }
  public String getName() {
    return "Servlet Context Lookup";
  }
}