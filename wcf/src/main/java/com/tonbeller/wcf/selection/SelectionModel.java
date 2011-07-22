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
package com.tonbeller.wcf.selection;

import java.util.Collection;
import java.util.Set;

/**
 * SelectionModel for Tree and Table components.
 * 
 * @author av
 */
public interface SelectionModel extends SingleSelectionModel {
  /** no selection */
  static final int NO_SELECTION = 0;
  /** radio buttons */
  static final int SINGLE_SELECTION = 1;
  /** check boxes */
  static final int MULTIPLE_SELECTION = 2;
  /** hyperlinks */
  static final int SINGLE_SELECTION_HREF = 3;
  /** use image buttons that look like checkboxes */
  static final int MULTIPLE_SELECTION_BUTTON = 4;
  /** hyperlinks */
  static final int MULTIPLE_SELECTION_HREF = 5;
  /** use image buttons that look like checkboxes */
  static final int SINGLE_SELECTION_BUTTON = 6;
  
  int getMode();
  void setMode(int mode);
  Set getSelection();
  void add(Object obj);
  void addAll(Collection c);
  void remove(Object obj);
  boolean contains(Object obj);
}
