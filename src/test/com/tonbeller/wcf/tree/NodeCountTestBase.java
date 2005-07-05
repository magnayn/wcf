package com.tonbeller.wcf.tree;

import junit.framework.TestCase;


/**
 * @author av
 */
public class NodeCountTestBase extends TestCase {
  protected void assertChildCount(TreeModel tm, int level, int count) {
    Object[] o = getChildren(tm, level);
    assertEquals("child count ", count, o.length);
  }

  protected Object [] getChildren(TreeModel tm, int level) {
    Object[] o = tm.getRoots();
    for (int i = 0; i < level; i++) {
      Object parent = o[0];
      assertTrue("has children ", tm.hasChildren(parent));
      o = tm.getChildren(parent);
    }
    return o;
  }

  protected void assertChildCount(TreeModel tm, int[] path, int count) {
    Object[] o = getChildren(tm, path);
    assertEquals("child count ", count, o.length);
  }

  protected Object [] getChildren(TreeModel tm, int[] path) {
    Object[] o = tm.getRoots();
    for (int i = 0; i < path.length; i++) {
      Object parent = o[path[i]];
      assertTrue("has children ", tm.hasChildren(parent));
      o = tm.getChildren(parent);
    }
    return o;
  }
  
  protected void assertLevelCount(TreeModel tm, int level) {
    Object[] o = getChildren(tm, level-1);
    assertFalse("leaf expected ", tm.hasChildren(o[0]));
  }

  protected void testParentChildConsistence(TreeModel ttm) {
    Object[] roots = ttm.getRoots();
    testParentChildConsistence(ttm, null, roots);
  }
  
  protected void testParentChildConsistence(TreeModel ttm, Object parent, Object[] children) {
    for (int i = 0; i  < children.length; i++) {
      Object child = children[i];
      assertEquals(parent, ttm.getParent(child));
      if (ttm.hasChildren(child))
        testParentChildConsistence(ttm, child, ttm.getChildren(child));
    }
  }
}
