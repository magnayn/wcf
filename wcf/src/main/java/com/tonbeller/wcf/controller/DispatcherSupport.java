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
package com.tonbeller.wcf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Fires events to listeners depending on parameters in the HttpServletRequest.
 * <ul>
 * <li>If a listener is registered with name <em>and</em> value, it receives the event
 * if the request contains a paramter with that name and value.
 * <li>If a listener is registered with name only, it receives the event if the request
 * contains a parameter with that name and any value.
 * <li>If a listener is registered with value only, it receives the event if the request
 * contains a parameter with that value and any name.
 * <li>If a listener is registered with no name and no value, it receives every request.
 * </ul>
 */
public class DispatcherSupport implements Dispatcher {

  // key = listener, value = name/value pair separated by '='
  private HashMap map = new HashMap();

  // key = name/value pair, value = List of listeners
  private HashMap inverseMap = new HashMap();

  /**
   * Adds a listener. A listener can only be registered once.
   * If its registered more than one, the last name/value
   * pair will be used.
   *
   * @param name name of the request parameter or null
   * @param value of the request parameter or null
   * @param listener the listener to register
   */
  public void addRequestListener(String name, String value, RequestListener listener) {
    removeRequestListener(listener);
    String key = getKey(name, value);
    map.put(listener, key);
    List list = (List) inverseMap.get(key);
    if (list == null) {
      list = new LinkedList();
      inverseMap.put(key, list);
    }
    list.add(listener);
  }

  /**
   * removes a listener.
   * @param listener the listener to remove
   */
  public void removeRequestListener(RequestListener listener) {
    String key = (String) map.get(listener);
    if (key != null) {
      map.remove(listener);
      List list = (List) inverseMap.get(key);
      if (list != null)
        list.remove(listener);
    }
  }

  /**
   * removes all listeners
   */
  public void clear() {
    map.clear();
    inverseMap.clear();
  }

  String getKey(String name, String value) {
    if ((name != null) && (value != null)) {
      return name + "=" + value;
    } else if (name != null) {
      return name + "=";
    } else if (value != null) {
      return "=" + value;
    }
    return "=";
  }

  /**
   * finds all RequestListeners that match a name/value pair in requestParameters
   * 
   * @param requestParameters HttpServletRequest.getParameterMap()
   * @return List of RequestListeners
   */
  List findAll(Map requestParameters) {
    List match = new LinkedList();
    Iterator it = requestParameters.keySet().iterator();

    while (it.hasNext()) {
      String name = (String) it.next();
      String[] values = (String[]) requestParameters.get(name);

      if (values != null) {
        for (int i = 0; i < values.length; i++) {

          // empty string will be caught later, when we test for the name only
          if (values[i] == null || values[i].length() == 0)
            continue;

          // try name and value
          Object obj = inverseMap.get(name + "=" + values[i]);

          if (obj != null) {
            match.addAll((List) obj);
          }

          // try value only
          obj = inverseMap.get("=" + values[i]);

          if (obj != null) {
            match.addAll((List) obj);
          }
        }
      }

      // try name only
      Object obj = inverseMap.get(name + "=");
      if (obj != null)
        match.addAll((List) obj);

      // imagebutton support
      if (name.endsWith(".x")) {
        name = name.substring(0, name.length() - 2);
        obj = inverseMap.get(name + "=");
        if (obj != null)
          match.addAll((List) obj);
      }
    }

    // try default handler
    Object obj = inverseMap.get("=");

    if (obj != null) {
      match.addAll((List) obj);
    }

    return match;
  }

  /**
   * fires event to all matching listeners
   * @param request the current request
   * @throws Exception the exception from listeners
   */
  public void request(RequestContext context) throws Exception {
    Iterator it = findAll(context.getRequest().getParameterMap()).iterator();

    while (it.hasNext()) {
      RequestListener listener = (RequestListener) it.next();
      listener.request(context);
    }
  }

  /**
   * returns the leaf RequestListeners that would be invoked for a http request
   * containing <code>httpParams</code>
   */
  public List findMatchingListeners(Map httpParams) {
    List candidates = findAll(httpParams);
    List result = new ArrayList();
    for (Iterator it = candidates.iterator(); it.hasNext();) {
      Object obj = it.next();
      if (obj instanceof Dispatcher) {
        Dispatcher d = (Dispatcher) obj;
        result.addAll(d.findMatchingListeners(httpParams));
      } else {
        result.add(obj);
      }
    }
    return result;
  }

}