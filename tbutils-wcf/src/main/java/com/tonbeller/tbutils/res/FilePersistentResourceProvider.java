package com.tonbeller.tbutils.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * A ResourceProvider that allows to store name/value pairs in the filesystem.
 * @author av
 * 
 * @since 18.01.2005
 */
class FilePersistentResourceProvider implements PersistentResourceProvider {
  Properties props;
  File file;

  private static Logger logger = Logger.getLogger(FilePersistentResourceProvider.class);

  public FilePersistentResourceProvider(File file) {
    this.file = file;
    load();
  }

  private void load() {
    props = new Properties();
    InputStream is = null;
    try {
      if (!file.exists())
        return;
      is = new FileInputStream(file);
      props.load(is);
    } catch (IOException e) {
      logger.error(null, e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          logger.error(null, e);
        }
      }
    }
  }

  private void store() {
    OutputStream os = null;
    try {
      os = new FileOutputStream(file);
      props.store(os, "## TONBELLER");
    } catch (IOException e) {
      logger.error(null, e);
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e1) {
          logger.error(null, e1);
        }
      }
    }

  }

  public String getString(String key) {
    return props.getProperty(key);
  }

  public void store(String key, String value) {
    props.setProperty(key, value);
  }

  public void remove(String key) {
    props.remove(key);
  }

  public Collection keySet() {
    return props.keySet();
  }

  public void flush() {
    store();
  }

  public void close() {
    store();
  }

  public void dump(Dumper d) {
    d.dump(this);
  }

  public String getName() {
    return "FileResouceProvider " + file;
  }
}