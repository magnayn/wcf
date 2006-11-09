/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;

import java.util.ArrayList;
import java.util.List;

public class GroupItemSupport extends ItemSupport implements GroupItem {
  List children = new ArrayList();
  
  public List getChildren() {
    return children;
  }

  public GroupItemSupport() {
  }

  public GroupItemSupport(String label) {
    super(label);
  }

  public GroupItemSupport(String label, String image) {
    super(label, image);
  }


}
