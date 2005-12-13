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
  public static final String JNDI_NULL = "jndi.null";

  public String getString(String key) {
    if (disabled)
      return null;
    try {
      if (context == null) {
        Context initCtx = new InitialContext();
        context = (Context) initCtx.lookup("java:comp/env");
      }
      Object obj = context.lookup(key);
      if (obj == null) {
        if (logger.isInfoEnabled())
          logger.info("key is null: " + key);
        return null;
      }
      String str = obj.toString();
      
      // there MUST be a value in web.xml
      if (JNDI_NULL.equals(str)) {
        if (logger.isInfoEnabled())
          logger.info("key is jndi.null: " + key);
        return null;
      }
      
      return str;
    } catch (NameNotFoundException e) {
      if (logger.isInfoEnabled())
        logger.info("key not found: " + key);
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

  public void dump(Dumper d) {
    d.dump(this);
  }
  
  public String getName() {
    return "JNDI Lookup " + (disabled ? "disabled" : "enabled"); 
  }

}
