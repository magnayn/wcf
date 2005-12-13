/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.param;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * implements a map that maps to the sqlValue property of
 * the SessionParam's in the SessionParamPool.
 * Not all methods are implemented but enough to use
 * the map as a generic target for wcf forms.
 * <p />
 * In a wcf:form xml definition, e.g. you can set the modelReference
 * attribute to "sqlValue.foo". Then the user will be able to
 * change the sqlValue of the SessionParam named "foo". 
 * <p/>
 * If you put a value into the map, the corresponding SqlParam
 * will be created.
 * 
 * @author av
 */
public class SqlValueMap implements Map {
  
  private SessionParamPool pool;

  SqlValueMap(SessionParamPool pool) {
    this.pool = pool;
  }

  public int size() {
    return pool.size();
  }

  public void clear() {
    pool.clear();
  }
  
  public boolean isEmpty() {
    return pool.isEmpty();
  }

  public boolean containsKey(Object key) {
    return pool.getParam(String.valueOf(key)) != null;
  }

  public boolean containsValue(Object value) {
    throw new UnsupportedOperationException();
  }

  public Collection values() {
    throw new UnsupportedOperationException();
  }

  public void putAll(Map t) {
    for (Iterator it = t.entrySet().iterator(); it.hasNext();) {
      Map.Entry e = (Entry) it.next();
      this.put(e.getKey(), e.getValue());
    }
  }

  public Set entrySet() {
    throw new UnsupportedOperationException();
  }

  public Set keySet() {
    throw new UnsupportedOperationException();
  }

  public Object get(Object key) {
    SessionParam p = pool.getParam((String)key);
    if (p == null)
      return null;
    return p.getSqlValue();
  }

  public Object remove(Object key) {
    SessionParam p = (SessionParam) pool.removeParam(String.valueOf(key));
    if (p == null)
      return null;
    return p.getSqlValue();
  }

  public Object put(Object key, Object value) {
    SessionParam p = (SessionParam) pool.getParam(String.valueOf(key));
    if (p == null)
      p = new SessionParam();
    Object ret = p.getSqlValue();
    p.setName(String.valueOf(key));
    p.setSqlValue(value);
    pool.setParam(p);
    return ret;
  }

}
