package com.tonbeller.tbutils.res;

import java.util.Collection;
import java.util.Properties;

/**
 * Adapter for Properties
 */

class PropertyResourceProvider implements ResourceProvider {
  Properties props;

  PropertyResourceProvider(Properties props) {
    this.props = props;
  }
  
  public String getString(String key) {
    return props.getProperty(key);
  }

  public Collection keySet() {
    return props.keySet();
  }
  public void close() {
  }

}
