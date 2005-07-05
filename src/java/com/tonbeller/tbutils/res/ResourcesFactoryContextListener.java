package com.tonbeller.tbutils.res;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

/**
 * sets the locale for JSTL tags
 * 
 * @author av
 */
public class ResourcesFactoryContextListener implements ServletContextListener {

  private static Logger logger = Logger.getLogger(ResourcesFactoryContextListener.class);

  public void contextInitialized(ServletContextEvent e) {
    try {
      Locale fixedLocale = ResourcesFactory.instance().getFixedLocale();
      // if appLocale is set, make locale available to JSTL tags
      if (fixedLocale != null) {
        logger.info("setting application locale to " + fixedLocale);
        ServletContext sc = e.getServletContext();
        Config.set(sc, Config.FMT_LOCALE, fixedLocale);
      }
    } catch (Exception ex) {
      logger.error("Initialize Factory", ex);
    }
  }

  public void contextDestroyed(ServletContextEvent e) {
  }

}