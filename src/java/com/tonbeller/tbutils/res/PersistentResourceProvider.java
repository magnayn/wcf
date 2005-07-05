package com.tonbeller.tbutils.res;

/**
 * @author av
 * @since 18.01.2005
 */
public interface PersistentResourceProvider extends ResourceProvider {
  /**
   * sets a key/value pair to the storage
   */
  public void store(String key, String value);
  /**
   * removes a key
   */
  public void remove(String key);
  /**
   * commits changes into the storage.
   */
  public void flush();
}
