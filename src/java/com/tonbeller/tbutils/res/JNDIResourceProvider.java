package com.tonbeller.tbutils.res;

import java.util.Collection;
import java.util.Collections;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

import org.apache.log4j.Logger;
/**
 * Adapter for java naming
 */

public class JNDIResourceProvider implements ResourceProvider {
  Context context = null;
  boolean disabled = false;
  private static Logger logger = Logger.getLogger(JNDIResourceProvider.class);  
  public String getString(String key) {
    if (disabled)
      return null;
    try {
      if (context == null) {
        Context initCtx = new InitialContext();
        context = (Context) initCtx.lookup("java:comp/env");
      }
      Object obj = context.lookup(key);
      if (obj == null)
        return null;
      return obj.toString();
    } catch (NameNotFoundException e) {
      return null;
    } catch (NoInitialContextException e) {
      logger.warn("JNDI Context not found, assuming test environment");
      disabled = true;
      return null;
    } catch (NamingException e) {
      logger.error(key, e);
      return null;
    }
  }

  public Collection keySet() {
    return Collections.EMPTY_SET;
  }
  
  public void close() {
    try {
      if (context != null)
        context.close();
    } catch (NamingException e) {
      logger.error("error closing context", e);
    } finally {
      context = null;
    }
  }

}
