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
package com.tonbeller.wcf.sqltable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.tonbeller.wcf.controller.Controller;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestListener;

/**
 * @author av
 * @since Oct 6, 2004
 */
public class SqlTable implements HttpSessionBindingListener {
  String orderBy;
  String nestedOrderBy;
  boolean descending;
  String id;
  int maxRows = -1; // -1 means all
  int startRow = 0; // first row is 0
  Dispatcher dispatcher = new DispatcherSupport();
  int counter;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isDescending() {
    return descending;
  }

  public void setDescending(boolean descending) {
    this.descending = descending;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public int getMaxRows() {
    return maxRows;
  }

  public void setMaxRows(int maxRows) {
    this.maxRows = maxRows;
  }

  public int getStartRow() {
    return startRow;
  }

  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }

  public void valueBound(HttpSessionBindingEvent ev) {
    Controller.instance(ev.getSession()).addRequestListener(dispatcher);
  }

  public void valueUnbound(HttpSessionBindingEvent ev) {
    dispatcher.clear();
  }

  public void addRequestListener(String name, String value, RequestListener rl) {
    dispatcher.addRequestListener(name, value, rl);
  }

  public void clear() {
    dispatcher.clear();
    counter = 0;
  }

  public String nextId() {
    counter += 1;
    return id + "." + counter;
  }

  public String toString() {
    return "SqlTable[id=" + id + ", orderBy=" + orderBy + ", descending=" + descending
        + ", maxRows=" + maxRows + ", startRow=" + startRow + "]";
  }
  public String getNestedOrderBy() {
    return nestedOrderBy;
  }
  public void setNestedOrderBy(String nestedOrderBy) {
    this.nestedOrderBy = nestedOrderBy;
  }
}
