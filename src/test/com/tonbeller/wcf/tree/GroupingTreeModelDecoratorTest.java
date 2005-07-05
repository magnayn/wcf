package com.tonbeller.wcf.tree;

import java.util.Comparator;

import com.tonbeller.wcf.tree.GroupingTreeModelDecorator.Group;
import com.tonbeller.wcf.tree.TestTreeModel.Node;



public class GroupingTreeModelDecoratorTest extends NodeCountTestBase {
  TreeModel ttm ;
  GroupingTreeModelDecorator gtm;

  public void testGrouping() {
    
    ttm = new TestTreeModel(new int[] {10});
    testParentChildConsistence(ttm);
    
    // 2 pages 5 items each
    gtm = new GroupingTreeModelDecorator (new DefaultLabelProvider(), ttm, 5);
    testParentChildConsistence(gtm);

    assertLevelCount(gtm, 2);
    assertChildCount(gtm, 0, 2);
    assertChildCount(gtm, 1, 5);

    // 4, 4, 2 items
    gtm = new GroupingTreeModelDecorator (new DefaultLabelProvider(), ttm, 4);
    testParentChildConsistence(gtm);
    assertLevelCount(gtm, 2);
    Object[] o = gtm.getRoots();
    assertEquals("root count", 3, o.length);
    assertEquals(4, gtm.getChildren(o[0]).length);
    assertEquals(4, gtm.getChildren(o[1]).length);
    assertEquals(2, gtm.getChildren(o[2]).length);
    
  }

  
  public void testHierarchically() {
    ttm = new TestTreeModel(new int[] {9, 11});
    gtm = new GroupingTreeModelDecorator (new DefaultLabelProvider(), ttm, 5);
    testParentChildConsistence(ttm);
    assertLevelCount(gtm, 4);
    // roots = group A ...
    //              A
    //                group A[0] ...
    //                        A[0]
    //                        A[1]
    //                        ...
    //                        A[4]
    //                group A[5] ...
    //                        A[5]
    //                        A[6]
    //                        A[7]
    //                        A[8]
    //                        A[9]
    //                group A[10] ...
    //                        A[10]
    //              B
    //              C
    //              D
    //              E
    //         group F ...
    //              F
    //              G
    //              H
    //              I
    
    // level 0 = roots
    assertChildCount(gtm, new int[]{}, 2);
    // level 1
    assertChildCount(gtm, new int[]{0}, 5);
    assertChildCount(gtm, new int[]{1}, 4);
    // level 2
    assertChildCount(gtm, new int[]{0, 0}, 3);
    assertChildCount(gtm, new int[]{0, 1}, 3);
    assertChildCount(gtm, new int[]{0, 2}, 3);
    assertChildCount(gtm, new int[]{0, 3}, 3);
    assertChildCount(gtm, new int[]{0, 4}, 3);
    assertChildCount(gtm, new int[]{1, 0}, 3);
    assertChildCount(gtm, new int[]{1, 1}, 3);
    assertChildCount(gtm, new int[]{1, 2}, 3);
    assertChildCount(gtm, new int[]{1, 3}, 3);
    // level 3    
    assertChildCount(gtm, new int[]{0, 0, 0}, 5);
    assertChildCount(gtm, new int[]{0, 0, 1}, 5);
    assertChildCount(gtm, new int[]{0, 0, 2}, 1);
  }

  public void testCaching() {
    ttm = new TestTreeModel(new int[] {4, 4});
    gtm = new GroupingTreeModelDecorator (new DefaultLabelProvider(), ttm, 2);

    Object[] o = gtm.getRoots();
    Object[] r = o;
    Group g0 = (Group) o[0];
    o = gtm.getChildren(o[0]);
    o = gtm.getChildren(o[0]);
    Group g1 = (Group) o[0];

    // repeat
    o = gtm.getRoots();
    assertEquals(r, o);
    assertEquals(g0, o[0]);
    o = gtm.getChildren(o[0]);
    o = gtm.getChildren(o[0]);
    assertEquals(g1, o[0]);
    
    ttm.fireModelChanged(true);
    o = gtm.getRoots();
    assertNotSame(r, o);
    assertNotSame(g0, o[0]);
    o = gtm.getChildren(o[0]);
    o = gtm.getChildren(o[0]);
    assertNotSame(g1, o[0]);
  }
  
  /** fetch parent without fetching its children before */
  public void testExpandSelected() {
    ttm = new TestTreeModel(new int[] {10});
    Object[] o = ttm.getRoots();
    gtm = new GroupingTreeModelDecorator (new DefaultLabelProvider(), ttm, 5);
    Object g = gtm.getParent(o[0]);
    assertNotNull(g);
    assertTrue("Group expected ", g instanceof Group);
    assertNull(gtm.getParent(g));
  }

  /** very many nodes need more than additional level */
  public void testRecursive() {
    ttm = new TestTreeModel(new int[] {18});
    gtm = new GroupingTreeModelDecorator (new DefaultLabelProvider(), ttm, 3);

    // the current implementation puts the smallest group on top:
    // g1
    //   g11
    //     n1
    //     n2
    //     n3
    //   g12
    //     n4
    //     n5
    //     n6
    //   g13
    //     n7
    //     n8
    //     n9
    // g2
    //   g21
    //     n10
    //     n11
    //     n12
    //   g22
    //     n13
    //     n14
    //     n15
    //   g23
    //     n16
    //     n17
    //     n18
    
    assertLevelCount(gtm, 3);
    Object[] o = gtm.getRoots();
    assertEquals(2, o.length);
    assertTrue(o[0] instanceof Group);
    
    assertTrue(gtm.hasChildren(o[0]));
    o = gtm.getChildren(o[0]);
    assertEquals(3, o.length);
    assertTrue(o[0] instanceof Group);

    assertTrue(gtm.hasChildren(o[0]));
    o = gtm.getChildren(o[0]);
    assertEquals(3, o.length);
    assertTrue(o[0] instanceof Node);
  }
  
  /* ========================= TreeMap test ===================== */

  static class VolatileNode {
    String label;
    VolatileNode(String label) {
      this.label = label;
    }
    public String toString() {
      return label;
    }
  }
  static class NodeComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      String s1 = ((VolatileNode)o1).toString();
      String s2 = ((VolatileNode)o2).toString();
      return s1.compareTo(s2);
    }
  }

  /**
   * a tree model that always creates new node instances that
   * are considered "the same" by the nodeComparator.
   */
  static class VolatileTreeModel extends AbstractTreeModel {
    public Object[] getRoots() {
      Object[] o = new Object[4];
      o[0] = new VolatileNode("A");
      o[1] = new VolatileNode("B");
      o[2] = new VolatileNode("C");
      o[3] = new VolatileNode("D");
      return o;
    }
    public boolean hasChildren(Object node) {
      VolatileNode n = (VolatileNode)node;
      return n.label.length() == 1;
    }
    public Object[] getChildren(Object node) {
      VolatileNode n = (VolatileNode)node;
      Object[] o = new Object[2];
      o[0] = new VolatileNode(n.label + "1");
      o[1] = new VolatileNode(n.label + "2");
      return o;
    }
    public Object getParent(Object node) {
      VolatileNode n = (VolatileNode)node;
      if (n.label.length() > 1) {
        String s = n.label.substring(0, 1);
        return new VolatileNode(s);
      }
      return null;
    }
  }
  
  /** exchange TreeModel */
  public void testTreeMap() {
    ttm = new VolatileTreeModel();
    gtm = new GroupingTreeModelDecorator (new NodeComparator(), new DefaultLabelProvider(), ttm, 2);

    Object[] o = gtm.getRoots();
    Object[] r = o;
    Group g0 = (Group) o[0];
    o = gtm.getChildren(o[0]);
    o = gtm.getChildren(o[0]);
    VolatileNode n1 = (VolatileNode) o[0];

    // repeat
    o = gtm.getRoots();
    assertEquals(r, o);
    assertEquals(g0, o[0]);
    o = gtm.getChildren(o[0]);
    o = gtm.getChildren(o[0]);
    VolatileNode n2 = (VolatileNode) o[0];
    assertEquals(n1, n2);
    
    ttm.fireModelChanged(true);
    o = gtm.getRoots();
    assertNotSame(r, o);
    assertNotSame(g0, o[0]);
    o = gtm.getChildren(o[0]);
    o = gtm.getChildren(o[0]);
    n2 = (VolatileNode) o[0];
    assertNotSame(n1, n2);
  }
}
