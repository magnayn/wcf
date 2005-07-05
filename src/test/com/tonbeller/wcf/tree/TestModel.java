/*
 * Created on 17.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.tonbeller.wcf.tree;

class TestModel extends AbstractTreeModel {
  N root;
  TestModel(N root) {
    this.root = root;
  }
  public Object[] getRoots() {
    return new Object[] { root };
  }
  public boolean hasChildren(Object node) {
    return ((N) node).children.size() > 0;
  }
  public Object[] getChildren(Object node) {
    return ((N) node).children.toArray();
  }
  public Object getParent(Object node) {
    return ((N) node).parent;
  }

  public static String print(TreeModel tm) {
    StringBuffer sb = new StringBuffer();
    Object[] roots = tm.getRoots();
    print(tm, sb, roots);
    return sb.toString();
  }
  
  public void fireModelChanged() {
    super.fireModelChanged();
  }

  private static void print(TreeModel tm, StringBuffer sb, Object[] nodes) {
    for (int i = 0; i < nodes.length; i++) {
      N n = (N) nodes[i];
      sb.append(n.getLabel());
      if (tm.hasChildren(n)) {
        sb.append("[");
        print(tm, sb, tm.getChildren(n));
        sb.append("]");
      }
      if (i < nodes.length - 1)
        sb.append(",");
    }
  }

  public N getRoot() {
    return root;
  }

  public void setRoot(N n) {
    root = n;
    fireModelChanged();
  }

}