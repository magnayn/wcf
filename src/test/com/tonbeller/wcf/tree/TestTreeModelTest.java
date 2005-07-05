package com.tonbeller.wcf.tree;



/**
 * @author av
 */
public class TestTreeModelTest extends NodeCountTestBase {
  public void testModel() {
    TestTreeModel ttm = new TestTreeModel();
    testParentChildConsistence(ttm);
    assertLevelCount(ttm, 6);
    assertChildCount(ttm, 0, 3);
    assertChildCount(ttm, 3, 4);
    
    ttm = new TestTreeModel(new int[] { 1, 2, 3 });
    testParentChildConsistence(ttm);
    assertLevelCount(ttm, 3);
    assertChildCount(ttm, 0, 1);
    assertChildCount(ttm, 1, 2);
    assertChildCount(ttm, 2, 3);
  }

}
