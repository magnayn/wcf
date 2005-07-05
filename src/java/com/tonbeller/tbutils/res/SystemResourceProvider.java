package com.tonbeller.tbutils.res;

import java.util.Collection;

/**
 * looksup keys via System.getProperty()
 * 
 * @author av
 */
public class SystemResourceProvider implements ResourceProvider {

  public SystemResourceProvider() {
    super();
  }
  public String getString(String key) {
    return System.getProperty(key);
  }

  public Collection keySet() {
    return System.getProperties().keySet();
  }
  public void close() {
  }

}
