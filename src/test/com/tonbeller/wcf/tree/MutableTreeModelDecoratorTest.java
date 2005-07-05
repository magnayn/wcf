package com.tonbeller.wcf.tree;

import com.tonbeller.wcf.tree.TestTreeModel.Node;

/**
 * MutableTreeModelDecorator uses ChangeOrderUtils which has its own tests. So we dont test
 * all index combinations.
 * 
 * @author av
 */
public class MutableTreeModelDecoratorTest extends NodeCountTestBase {
  TreeModel ttm;
  MutableTreeModelDecorator mtd;
  TestTreeModel.Node[] roots;
  TestTreeModel.Node[] children;

  void setUp(int n) {
    ttm = new TestTreeModel(new int[] { 1, n});
    mtd = new MutableTreeModelDecorator(ttm);
    roots = (Node[]) mtd.getRoots();
    children = (Node[]) mtd.getChildren(roots[0]);
  }

  protected void setUp() throws Exception {
    setUp(4);
  }

  void assertOrder(int[] ord) {
    assertEquals(children.length, ord.length);
    children = (Node[]) mtd.getChildren(roots[0]);
    for (int i = 0; i < ord.length; i++) {
      String s = "A[" + ord[i] + "]";
      assertEquals("node[" + i + "] = ", s, children[i].getName());
    }
  }
  void assertOrder(int i1, int i2, int i3, int i4) {
    assertOrder(new int[]{i1, i2, i3, i4});
  }
  
  void fwd(int i) {
    mtd.move(roots[0], children[i], i, i+1);
  }
  
  void bwd(int i) {
    mtd.move(roots[0], children[i], i, i-1);
  }
  
  void move(int oldIndex, int newIndex) {
    mtd.move(roots[0], children[oldIndex], oldIndex, newIndex);
  }

  public void testFwdBwd() {
    assertEquals(4, children.length);
    assertOrder(0, 1, 2, 3);
    bwd(0);
    assertOrder(0, 1, 2, 3);
    fwd(0);
    assertOrder(1, 0, 2, 3);
    bwd(0);
    assertOrder(1, 0, 2, 3);
    bwd(1);
    assertOrder(0, 1, 2, 3);
    bwd(1);
    assertOrder(1, 0, 2, 3);
    bwd(1);
    assertOrder(0, 1, 2, 3);
    fwd(3);
    assertOrder(0, 1, 2, 3);
    bwd(3);
    assertOrder(0, 1, 3, 2);
    bwd(3);
    assertOrder(0, 1, 2, 3);
    fwd(2);
    assertOrder(0, 1, 3, 2);
    fwd(2);
    assertOrder(0, 1, 2, 3);
  }
  
  public void testInterval() {
    assertEquals(4, children.length);
    assertOrder(0, 1, 2, 3);
    move(0, 0);
    move(1, 1);
    assertOrder(0, 1, 2, 3);
    move(0, 1);
    assertOrder(1, 0, 2, 3);
    move(0, 2);
    assertOrder(0, 2, 1, 3);
    move(0, 3);
    assertOrder(2, 1, 3, 0);
  }
}
