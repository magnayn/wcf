package com.tonbeller.wcf.wizard;

import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.ComponentTag;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * @author av
 */
public class WizardComponentTag extends ComponentTag {

  protected Component createComponent(RequestContext context) throws Exception {
    return new WizardComponent(id, null);
  }

}
