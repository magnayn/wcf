/*
 * Created on 17.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.tonbeller.wcf.tree;

import java.util.ArrayList;
import java.util.Iterator;

class N {
  String label;
  N parent;
  ArrayList children = new ArrayList();

  N(String label) {
    this.label = label;
  }

  N(String label, N left, N right) {
    this.label = label;
    if (left != null) {
      left.parent = this;
      children.add(left);
    }
    if (right != null) {
      right.parent = this;
      children.add(right);
    }
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(label);
    if (children.size() > 0) {
      sb.append("[");
      for (Iterator it = children.iterator(); it.hasNext();) {
        N n = (N) it.next();
        sb.append(n.toString());
        if (it.hasNext())
          sb.append(",");
      }
      sb.append("]");
    }
    return sb.toString();
  }

  /**
   * @return
   */
  public ArrayList getChildren() {
    return children;
  }

  /**
   * @return
   */
  public String getLabel() {
    return label;
  }

  /**
   * @return
   */
  public N getParent() {
    return parent;
  }

}