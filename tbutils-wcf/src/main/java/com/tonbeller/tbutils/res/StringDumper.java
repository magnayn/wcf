package com.tonbeller.tbutils.res;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** dumps into StringBuffer */

public class StringDumper implements Dumper {
  
  private static final String SEP = "===================================\n";
  StringBuffer sb = new StringBuffer();
  
  public StringDumper() {
  }
  
  /**
   * return false for properties that should not be visible in the log file,
   * e.g. passwords etc.
   */
  protected boolean accept(String name) {
    return true;
  }
  
  public void dump(ResourceProvider p) {
    if (p == null)
      return;
    sb.append(SEP);
    sb.append(p.getClass().getName()).append(": ").append(p.getName()).append("\n");
    sb.append(SEP);
    List keys = new ArrayList();
    keys.addAll(p.keySet());
    Collections.sort(keys);
    for (Iterator it = keys.iterator(); it.hasNext();) {
      String key = (String) it.next();
      if (!accept(key))
        continue;
      String val = p.getString(key);
      sb.append(key).append(" = ").append(val).append("\n");
    }
  }
  
  public String toString() {
    return sb.toString();
  }

}
