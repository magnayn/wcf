package com.tonbeller.wcf.tree;

import junit.framework.TestCase;
public class NodeFilterTest extends TestCase {

  public NodeFilterTest(String arg0) {
    super(arg0);
  }

  class MyNodeFilter implements NodeFilter {
    public boolean accept(Object node) {
      return !node.toString().endsWith("[1]");
    }
  }
  
  class MyFalseFilter implements NodeFilter {
    public boolean accept(Object node) {
      return false;
    }
  }

  public void testFilteringTreeModelDecorator() {
    TreeModel tm = new TestTreeModel();
    tm = new FilteringTreeModelDecorator(tm, new MyNodeFilter());
    Object[] roots = tm.getRoots();
    assertEquals(3, roots.length);
    Object[] a1 = tm.getChildren(roots[0]);
    assertEquals(3, a1.length);
    assertEquals("A[0]", a1[0].toString());
    assertEquals("A[2]", a1[1].toString());
    assertEquals("A[3]", a1[2].toString());

  }

  public void testAndFilter() {
    TreeModel tm = new TestTreeModel();
    AndNodeFilter anf = new AndNodeFilter();
    anf.add(new MyNodeFilter());
    anf.add(new MyFalseFilter());
    tm = new FilteringTreeModelDecorator(tm, anf);
    Object[] roots = tm.getRoots();
    assertEquals(0, roots.length);
  }

}
