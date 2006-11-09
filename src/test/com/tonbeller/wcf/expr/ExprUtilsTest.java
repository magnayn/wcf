package com.tonbeller.wcf.expr;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author av
 */
public class ExprUtilsTest extends TestCase {
  public class MyBean {
    Object object = null;
    int value = 4;
    public int getValue() {
      return value;
    }
    public void setValue(int newValue) {
      this.value = newValue;
    }
    public Object getObject() {
      return object;
    }
    public void setObject(Object object) {
      this.object = object;
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
    final Map map = new HashMap();
    map.put("bean", bean);
    
    ctx = new ExprContext() {
      public Object findBean(String name) {
        return map.get(name);
      }
      public void setBean(String name, Object value) {
        map.put(name, value);
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

  public void testSetBean1() {
    MyBean b = new MyBean();
    ExprUtils.setModelReference(ctx, "bean", b);
    Object o = ExprUtils.getModelReference(ctx, "bean");
    assertEquals(b, o);
    o = ExprUtils.getModelReference(ctx, "#{bean}");
    assertEquals(b, o);
    o = ExprUtils.getModelReference(ctx, "${bean}");
    assertEquals(b, o);
  }

  public void testSetBean2() {
    MyBean b = new MyBean();
    ExprUtils.setModelReference(ctx, "${bean}", b);
    Object o = ExprUtils.getModelReference(ctx, "${bean}");
    assertEquals(b, o);
  }
  
  public void testSetBean3() {
    Object obj1 = new Object();
    ExprUtils.setModelReference(ctx, "${bean.object}", obj1);
    assertEquals(obj1, bean.getObject());
    Object obj2 = ExprUtils.getModelReference(ctx, "${bean.object}");
    assertEquals(obj1, obj2);
  }
  
  public void testPropertyDescriptor() {
    PropertyDescriptor pd = ExprUtils.getPropertyDescriptor(ctx, "#{bean.value}");
    assertEquals(Integer.TYPE, pd.getPropertyType());
  }
}
