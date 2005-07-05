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
package com.tonbeller.wcf.catedit;

import java.util.EventObject;

public class CategoryModelChangeEvent extends EventObject {
  public CategoryModelChangeEvent(CategoryModel source) {
    super(source);
  }
  
  public CategoryModel getCategoryModel() {
    return (CategoryModel)getSource();
  }

}
