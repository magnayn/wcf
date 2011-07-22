package com.tonbeller.wcf.tree;

import junit.framework.TestCase;

/**
 * @author av
 */
public class TreeModelDecoratorTest extends TestCase {

  public TreeModelDecoratorTest(String arg0) {
    super(arg0);
  }

  N root;
  TestModel tree;

  NodeFilter filter = new NodeFilter() {
    public boolean accept(Object node) {
      return "*".equals(((N) node).getLabel());
    }
  };

  /*
   *                         a
   *                  /             \
   *                 b               c
   *              /     \         /     \
   *             d       e       f       *
   *            / \     / \     / \     / \
   *           h   *   j   k   l   m   n   o
   * 
   * * = significant
   */

  protected void setUp() throws Exception {
    root =
      new N(
        "a",
        new N("b", new N("d", new N("h"), new N("*")), new N("e", new N("j"), new N("k"))),
        new N("c", new N("f", new N("l"), new N("m")), new N("*", new N("n"), new N("o"))));
    tree = new TestModel(root);
    assertEquals("a[b[d[h,*],e[j,k]],c[f[l,m],*[n,o]]]", TestModel.print(tree));
  }

  public void testOptimizer() {
    
    OptimizingTreeModelDecorator opt = new OptimizingTreeModelDecorator(filter, tree);
    assertEquals("b[d[h,*]],c[*[n,o]]", TestModel.print(opt));
    
    opt.setOptimizeRoot(false);
    assertEquals("a[b[d[h,*]],c[*[n,o]]]", TestModel.print(opt));

    opt.setOptimizeLeafs(true);
    // System.out.println(TestModel.print(opt));
    assertEquals("a[b[d[*]],c[*]]", TestModel.print(opt));
  }
  
  public void testCachingOptimizer() {
    CachingTreeModelDecorator t1 = new CachingTreeModelDecorator(tree);
    OptimizingTreeModelDecorator opt = new OptimizingTreeModelDecorator(filter, t1);
    CachingTreeModelDecorator t2 = new CachingTreeModelDecorator(opt);
    assertEquals("b[d[h,*]],c[*[n,o]]", TestModel.print(t2));
    
    opt.setOptimizeRoot(false);
    assertEquals("a[b[d[h,*]],c[*[n,o]]]", TestModel.print(t2));

    opt.setOptimizeLeafs(true);
    // System.out.println(TestModel.print(opt));
    assertEquals("a[b[d[*]],c[*]]", TestModel.print(t2));
  }

  public void testMutable() {
    MutableTreeModelDecorator t0 = new MutableTreeModelDecorator(tree);
    CachingTreeModelDecorator t1 = new CachingTreeModelDecorator(t0);
    OptimizingTreeModelDecorator opt = new OptimizingTreeModelDecorator(filter, t1);
    CachingTreeModelDecorator t2 = new CachingTreeModelDecorator(opt);
    assertEquals("b[d[h,*]],c[*[n,o]]", TestModel.print(t2));
    
    opt.setOptimizeRoot(false);
    assertEquals("a[b[d[h,*]],c[*[n,o]]]", TestModel.print(t2));

    opt.setOptimizeLeafs(true);
    // System.out.println(TestModel.print(opt));
    assertEquals("a[b[d[*]],c[*]]", TestModel.print(t2));
    
    t0.move(root, root.getChildren().get(0), 0, 1);
    assertEquals("a[c[*],b[d[*]]]", TestModel.print(t2));
  }
  


}
