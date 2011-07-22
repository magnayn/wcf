package com.tonbeller.tbutils.res;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * initial provider. looks up variables
 * <ol>
 * <li>System.getProperty()</li>
 * <li>user.properties in root classpath</li>
 * <li>resfactory.properties in root classpath</li>
 * </ol>
 * in that order.
 * 
 * @author av
 */
public class SimpleInitialProvider extends CompositeResourceProvider implements InitialProvider {
  public SimpleInitialProvider() {
    add(new SystemResourceProvider());

    try {
      ResourceBundle resb = ResourceBundle.getBundle(USER_PROPERTIES);
      add(new BundleResourceProvider(USER_PROPERTIES, resb));
    }
    catch (MissingResourceException e) {
      // ignore
    }
    
    try {
      ResourceBundle resb = ResourceBundle.getBundle(RESFACTORY_PROPERTIES);
      add(new BundleResourceProvider(RESFACTORY_PROPERTIES, resb));
    }
    catch (MissingResourceException e) {
      // ignore
    }
  }
}