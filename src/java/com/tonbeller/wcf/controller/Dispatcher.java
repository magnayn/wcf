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
public interface Dispatcher extends RequestListener {

  /**
   * Adds a listener. A listener can only be registered once.
   * If its registered more than once, the last name/value
   * pair will be used.
   *
   * @param name name of the request parameter or null
   * @param value of the request parameter or null
   * @param listener the listener to register
   */
  void addRequestListener(String name, String value, RequestListener listener);

  /**
   * removes a listener.
   * @param listener the listener to remove
   */
  void removeRequestListener(RequestListener listener);

  /**
   * removes all listeners
   */
  void clear();

  /**
   * fires an event to all matching RequestListeners
   * @param context the current request
   * @throws Exception the exception from listeners
   */
  void request(RequestContext context) throws Exception;

  /**
   * returns the list of leaf RequestListeners that would be
   * invoked for a request with the given parameters.
   */
  List findMatchingListeners(Map httpParams);
  
}