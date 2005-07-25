package com.tonbeller.tbutils.res;

import java.util.Collection;
import java.util.Collections;

/**
 * Dummy that does not know any key/values
 */
public class NullResourceProvider implements ResourceProvider {

  public String getString(String key) {
    return null;
  }

  public Collection keySet() {
    return Collections.EMPTY_SET;
  }

  public void close() {
  }

  public void dump(Dumper d) {
    d.dump(this);
  }
  public String getName() {
    return "Empty Provider";
  }
}
