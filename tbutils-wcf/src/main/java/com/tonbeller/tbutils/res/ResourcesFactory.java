package com.tonbeller.tbutils.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * creates and caches {@link Resources}.
 * <p>
 * The factory is configured via four variables that are looked up in
 * <ol>
 * <li>in JNDI java:/comp/env context</li>
 * <li>resfactory.properties in root classpath</li>
 * <li>System.getProperty()</li>
 * </ol>
 * in that order. This may be overridden by defining a java system property
 * <code>tbeller.initialResourceProvider</code>, for example
 * <code>-Dtbeller.initialResourceProvider=com.tonbeller.tbutils.res.SystemResourceProvider</code>
 * will look up the initial variables in System properties only. This is meant
 * for testing outside the web environment.
 * <p>
 * 
 * <dl>
 * 
 * <dt>tbeller.locale</dt>
 * <dd>A string either 2 characters (e.g. "en") or 5 chars (e.g. "en_US"). If
 * present, the application will not care about the browsers locale but use this
 * locale instead.</dd>
 * 
 * <dt>tbeller.home</dt>
 * <dd>A directory that contains configuration files that have been modified by
 * the customer and other resources. If not set the default is
 * ${user.home}/.tbeller</dd>
 * 
 * <dt>tbeller.properties</dt>
 * <dd>whitespace separated list of property file names, relative to
 * TBELLER_HOME. These files will be loaded via the file system. Should be
 * defined in web.xml</dd>
 * 
 * <dt>tbeller.bundles</dt>
 * <dd>a whitespace separated list of ResourceBundle names that will be loaded
 * via ResourceBundle.getBundle. Should be defined in web.xml</dd>
 * 
 * </dl>
 * 
 * @author av
 */
public class ResourcesFactory {
  /**
   * If there is a ServletContext initParameter this provider will be used
   * instead of the JNDI Provider.
   */
  public static final String INITIAL_PROVIDER = "tbeller.InitialResourceProvider";

  // System Property: wenn "false", wird kein JNDIProvider genommen
  public static final String USE_JNDI_PROVIDER = "tbeller.usejndi";

  public static final String TBELLER_BUNDLES = "tbeller.bundles";

  public static final String TBELLER_PROPERTIES = "tbeller.properties";

  // env-entries to be overridden by deployer
  public static final String TBELLER_HOME = "tbeller.home";

  public static final String TBELLER_LOCALE = "tbeller.locale";

  private static Logger logger = Logger.getLogger(ResourcesFactory.class);

  Map locale2providerMap = new HashMap();

  File homeDir;

  Locale fixedLocale;

  String[] properties;

  String[] bundles;

  static ResourcesFactory instance = new ResourcesFactory();

  private ResourcesFactory() {
    initialize();
  }

  public static ResourcesFactory instance() {
    return instance;
  }

  /**
   * returns a <code>Resources</code> instance in the configured locale, or if
   * no locale was configured, in the browsers locale. If the package
   * <code>packg</code> contains a ResourceBundle named "resources.properties"
   * that bundle will contained in the returned Resources.
   */
  Resources getResources(Locale requestLocale, String bundleName) {
    Locale locale = fixedLocale;
    if (locale == null)
      locale = requestLocale;
    if (locale == null)
      locale = Locale.getDefault();

    CompositeResourceProvider crp = new CompositeResourceProvider();
    ResourceProvider baseProvider = getProvider(locale);
    crp.add(baseProvider);

    ResourceProvider bundleProvider = getProvider(locale, bundleName);
    if (bundleProvider != null)
      crp.add(bundleProvider);
    
    return new Resources(crp, locale, homeDir);
  }

  /**
   * if the application was configured to use a fixed locale independent of the
   * browsers locale, returns that locale. Returns null otherwise
   * 
   * @return locale or null
   */
  public Locale getFixedLocale() {
    return fixedLocale;
  }

  /**
   * liefert BundleProvider fuer das Package oder null, wenn kein bundle
   * vorhanden.
   */
  static ResourceProvider getProvider(Locale locale, String bundleName) {
    if (bundleName == null)
      return null;
    try {
      ResourceBundle resb = ResourceBundle.getBundle(bundleName, locale);
      return new BundleResourceProvider(bundleName, resb);
    } catch (MissingResourceException e) {
      return null;
    }
  }

  ResourceProvider getProvider(Locale locale) {
    String key = locale.getLanguage() + locale.getCountry() + locale.getVariant();
    ResourceProvider p = (ResourceProvider) locale2providerMap.get(key);
    if (p != null)
      return p;
    p = createProvider(locale);
    locale2providerMap.put(key, p);
    return p;
  }

  void initialize() {
    ResourceProvider p = getInitialProvider();
    try {
      initialize(p);
    } finally {
      p.close();
    }
  }
  
  private ResourceProvider getInitialProvider() {
    ResourceProvider m = new SimpleInitialProvider();
    String clazz = m.getString(INITIAL_PROVIDER);
    if (clazz != null) {
      try {
        return (ResourceProvider) Class.forName(clazz).newInstance();
      } catch (Exception e) {
        logger.error("could not instantiate " + clazz, e);
      }
    }
    if ("false".equals(m.getString(USE_JNDI_PROVIDER)))
      return new SimpleInitialProvider();
    return new JNDIInitialProvider();
  }

  void initialize(ResourceProvider resp) {
    locale2providerMap.clear();
    
    ResourceProvider p = new ReplacingResourceProvider(resp);
    bundles = tokenize(p.getString(TBELLER_BUNDLES));
    
    // home directory may be overridden in ResourceBundle 
    CompositeResourceProvider c = new CompositeResourceProvider();
    c.add(p);
    addBundleProviders(Locale.getDefault(), c);
    p = new ReplacingResourceProvider(c);

    initHome(p);
    initLocale(p);
    properties = tokenize(p.getString(TBELLER_PROPERTIES));
  }

  String[] tokenize(String s) {
    if (s == null)
      return new String[0];
    StringTokenizer st = new StringTokenizer(s);
    String[] a = new String[st.countTokens()];
    for (int i = 0; i < a.length; i++)
      a[i] = st.nextToken();
    return a;
  }

  CompositeResourceProvider createProvider(Locale locale) {
    CompositeResourceProvider c = new CompositeResourceProvider();
    c.add(new SystemResourceProvider());
    addPropertyProviders(locale, c);
    addBundleProviders(locale, c);
    return c;
  }

  /**
   * @param locale
   * @param c
   */
  private void addPropertyProviders(Locale locale, CompositeResourceProvider c) {
    for (int i = 0; i < properties.length; i++) {
      File f = propertyFile(properties[i], locale);
      Properties props = new Properties();
      FileInputStream fis = null;
      try {
        fis = new FileInputStream(f);
        props.load(fis);
        c.add(new PropertyResourceProvider(f.getCanonicalPath(), props));
      } catch (FileNotFoundException e) {
        // ignore
        if (logger.isInfoEnabled())
          logger.info("optional property file not found: " + f.getAbsolutePath());
      } catch (IOException e) {
        logger.error("error loading " + f.getAbsolutePath(), e);
      } finally {
        try {
          if (fis != null)
            fis.close();
        } catch (IOException e2) {
          logger.error("error closing " + f.getAbsolutePath(), e2);
        }
      }
    }
  }

  File propertyFile(String propertyName, Locale locale) {
    String fn;
    File f = new File(propertyName);
    if (f.isAbsolute())
      fn = f.getAbsolutePath();
    else
      fn = homeDir.getAbsolutePath() + File.separator + propertyName;
    return findFile(fn, locale);
  }
  
  /**
   * @param locale
   * @param c
   */
  private void addBundleProviders(Locale locale, CompositeResourceProvider c) {
    for (int i = 0; i < bundles.length; i++) {
      try {
        ResourceBundle rb = ResourceBundle.getBundle(bundles[i], locale);
        c.add(new BundleResourceProvider(bundles[i], rb));
      } catch (MissingResourceException e) {
        // ignore
        if (logger.isInfoEnabled())
          logger.info("optional resource bundle not found: " + bundles[i]);
      }
    }
  }

  File findFile(String path, Locale locale) {
    String ext = "";

    int pos = path.lastIndexOf('.');
    if (pos >= 0)
      ext = path.substring(pos, path.length()); // including the dot
    path = path.substring(0, pos);

    File f = new File(path + "_" + locale.getLanguage() + "_" + locale.getCountry() + ext);
    if (f.exists())
      return f;

    f = new File(path + "_" + locale.getLanguage() + ext);
    if (f.exists())
      return f;

    return new File(path + ext);
  }

  void initHome(ResourceProvider p) {
    String dir = p.getString(TBELLER_HOME);
    if (dir == null || dir.trim().length() == 0)
      dir = System.getProperty("user.home") + File.separator + ".tonbeller";
    homeDir = new File(dir);
    homeDir.mkdirs();
  }

  void initLocale(ResourceProvider p) {
    fixedLocale = null;
    String lang = p.getString(TBELLER_LOCALE);
    if (lang != null) {
      lang = lang.trim();
      if (lang.length() == 2)
        fixedLocale = new Locale(lang, lang.toUpperCase());
      else if (lang.length() == 5) {
        String l = lang.substring(0, 2);
        String c = lang.substring(3, 5);
        fixedLocale = new Locale(l, c);
      } else if ("browser".equals(lang))
        ; // use browser settings
      // else use browser settings too.
    }
  }

}