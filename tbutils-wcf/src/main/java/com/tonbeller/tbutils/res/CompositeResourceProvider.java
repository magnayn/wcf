package com.tonbeller.tbutils.res;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Composite ResourceProvider.
 * 
 * Contains an ordered list of {@link ResourceProvider}'s. For a given key
 * it iterates the list and returns the value of the first ResourceProvider
 * that knows about the key.
 * <p />
 */

public class CompositeResourceProvider implements ResourceProvider {
  List list = new ArrayList();

  public String getString(String key) {
    for (Iterator it = list.iterator(); it.hasNext();) {
      ResourceProvider r = (ResourceProvider) it.next();
      String s = r.getString(key);
      if (s != null)
        return s;
    }
    return null;
  }

  public void add(ResourceProvider r) {
    list.add(r);
  }

  public void add(int index, ResourceProvider r) {
    list.add(index, r);
  }

  public List getProviders() {
    return list;
  }

  public Collection keySet() {
    Set set = new HashSet();
    for (Iterator it = list.iterator(); it.hasNext();) {
      ResourceProvider r = (ResourceProvider) it.next();
      set.addAll(r.keySet());
    }
    return set;
  }

  public void close() {
    for (Iterator it = list.iterator(); it.hasNext();) {
      ResourceProvider r = (ResourceProvider) it.next();
      r.close();
    }
  }

  public void dump(Dumper d) {
    for (Iterator it = list.iterator(); it.hasNext();) {
      ResourceProvider r = (ResourceProvider) it.next();
      r.dump(d);
    }
  }

  public String getName() {
    return "CompositeResourceProvider";
  }
}