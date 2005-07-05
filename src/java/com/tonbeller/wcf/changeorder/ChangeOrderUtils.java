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
package com.tonbeller.wcf.changeorder;

import java.util.List;

/**
 * @author av
 */
public class ChangeOrderUtils {

  /**
   * validates that oldIndex and newIndex fit into an array of size
   * @return true when the indexes are valid
   */
  static boolean checkBounds(int size, int oldIndex, int newIndex) {
    if (oldIndex < 0 || oldIndex >= size)
      return false;
    if (newIndex < 0 || newIndex >= size)
      return false;
    return true;
  }
  
  public static boolean move(List list, int oldIndex, int newIndex) {
    if (!checkBounds(list.size(), oldIndex, newIndex))
        return false;
    Object item = list.remove(oldIndex);
    list.add(newIndex, item);
    return true;
  }


  public static boolean move(Object[] array, int oldIndex, int newIndex) {
    if (!checkBounds(array.length, oldIndex, newIndex))
      return false;
    if (oldIndex == newIndex)
      return true;
    Object item = array[oldIndex];
    if (oldIndex < newIndex) {
      for (int i = oldIndex; i < newIndex; i++)
        array[i] = array[i+1];
    }
    else {
      for (int i = oldIndex; i > newIndex; i--) 
        array[i] = array[i-1];
    }
    array[newIndex] = item;
    
    return true;
  }

  public static int indexOf(Object[] array, Object item) {
    for (int i = 0; i < array.length; i++)
      if (item.equals(array[i]))
        return i;
    return -1;
  }

}
