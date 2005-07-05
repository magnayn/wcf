package com.tonbeller.wcf.form;

import org.w3c.dom.Element;

import com.tonbeller.wcf.component.FormListener;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * Created on 28.11.2002
 * 
 * @author av
 */
public class TestNodeHandler extends NodeHandlerSupport implements FormListener {
  int renderCount = 0;
  int revertCount = 0;
  int validateCount = 0;
    
  public void initialize(RequestContext context, XmlComponent comp, Element element) throws Exception {
    super.initialize(context, comp, element);
    comp.addFormListener(this);
  }

  public void render(RequestContext context) throws Exception {
    super.render(context);
    ++ renderCount;
  }

  public void revert(RequestContext context) {
    ++ revertCount;
  }

  public boolean validate(RequestContext context) {
    ++ validateCount;
    return true;
  }

}
