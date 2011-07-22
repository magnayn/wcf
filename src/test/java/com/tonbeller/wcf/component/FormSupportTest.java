package com.tonbeller.wcf.component;

import junit.framework.TestCase;

import com.tonbeller.wcf.controller.RequestContext;
public class FormSupportTest extends TestCase {
	Form f1, f2;
	MyFormListener l1, l2;

  class MyFormListener implements FormListener {
    int validateCount = 0;
    int revertCount = 0;
    public boolean validate(RequestContext context) {
      validateCount++;
      return true;
    }
    public void revert(RequestContext context) {
      revertCount++;
    }
  };
  
  public FormSupportTest(String name) {
    super(name);
  }
  
  public void testSingle() {
    // add twice, must be ignored
    f1.addFormListener(l1);
    f1.addFormListener(l1);
    f1.validate(null);
    assertEquals(1, l1.validateCount);
    f1.removeFormListener(l1);
    f1.validate(null);
    assertEquals(1, l1.validateCount);
  }

  public void testValidate1() {
  	// f2 listens to f1
  	f1.addFormListener(f2);
  	f1.validate(null);
  	assertEquals(1, l1.validateCount);
		assertEquals(1, l2.validateCount);
		f2.validate(null);
		assertEquals(1, l1.validateCount);
		assertEquals(2, l2.validateCount);
  }
	public void testValidate2() {
		// f1 listens to f2
		f1.addFormListener(f2);
		f2.addFormListener(f1);
		f1.validate(null);
		assertEquals(1, l1.validateCount);
		assertEquals(1, l2.validateCount);
		f2.validate(null);
		assertEquals(2, l1.validateCount);
		assertEquals(2, l2.validateCount);
  }

	public void testRevert1() {
		// f2 listens to f1
		f1.addFormListener(f2);
		f1.revert(null);
		assertEquals(1, l1.revertCount);
		assertEquals(1, l2.revertCount);
		f2.revert(null);
		assertEquals(1, l1.revertCount);
		assertEquals(2, l2.revertCount);
	}
	
	public void testRevert2() {
		// f1 listens to f2
		f1.addFormListener(f2);
		f2.addFormListener(f1);
		f1.revert(null);
		assertEquals(1, l1.revertCount);
		assertEquals(1, l2.revertCount);
		f2.revert(null);
		assertEquals(2, l1.revertCount);
		assertEquals(2, l2.revertCount);
	}

 
  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
		f1 = new FormSupport();
		l1 = new MyFormListener();
		f1.addFormListener(l1);

		f2 = new FormSupport();
		l2 = new MyFormListener();
		f2.addFormListener(l2);
  }

}
