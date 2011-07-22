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
package com.tonbeller.wcf.table;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.format.FormatException;

/**
 * renders the content of a cell. If you create your own renderer, you put it into
 * the TableColumn object for the column.
 */
public interface CellRenderer {
  public void render(RequestContext context, Element td, Object cell) throws FormatException;
}
