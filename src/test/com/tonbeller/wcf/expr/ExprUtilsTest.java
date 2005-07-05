package com.tonbeller.wcf.expr;

import junit.framework.TestCase;

/**
 * @author av
 */
public class ExprUtilsTest extends TestCase {
  public class MyBean {
    int value = 4;
    public int getValue() {
      return value;
    }
    public void setValue(int newValue) {
      this.value = newValue;
    }
  }
  
  MyBean bean;
  ExprContext ctx;
  
  /**
   * Constructor for ExprUtilsTest.
   * @param arg0
   */
  public ExprUtilsTest(String arg0) {
    super(arg0);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    bean = new MyBean();
    ctx = new ExprContext() {
      public Object findBean(String name) {
        return bean;
      }
    };
  }

  public void testCheckExpr() {
    try {
      ExprUtils.checkExpr("${abc.def");
      assertTrue("Exception expected", false);
    }
    catch (IllegalArgumentException e) {
    }

    try {
      ExprUtils.checkExpr("${abc.def}");
    }
    catch (IllegalArgumentException e) {
      assertTrue("unexpected exception", false);
    }
  }

  public void testGetModelReference() {
    Object obj = ExprUtils.getModelReference(ctx, "${bean.value}");
    assertEquals(new Integer(bean.getValue()), obj);
    obj = ExprUtils.getModelReference(ctx, "bean");
    assertEquals(bean, obj);
  }
  
  public void testBeanName() {
    assertEquals("bean", ExprUtils.getBeanName("${bean.property}"));
  }

  public void testSetModelReference() {
    ExprUtils.setModelReference(ctx, "${bean.value}", new Integer(27));
    assertEquals(27, bean.getValue());
  }

}
