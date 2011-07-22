package com.tonbeller.wcf.wizard;

import com.tonbeller.wcf.component.Renderable;
import com.tonbeller.wcf.component.Visible;

/**
 * The wizard will register a PageListener with all its pages. If a page is shown, its render() method
 * is called. When the processing of a page has finished, the page must call one of the PageListener 
 * methods to inform the Wizard.
 * 
 * @author av
 */
public interface WizardPage extends Renderable, Visible {
  public static class WizardPagePosition {
    private String name;
    private WizardPagePosition(String name) {
      this.name = name;
    }
    public static final WizardPagePosition SINGLE_PAGE = new WizardPagePosition("single");
    public static final WizardPagePosition FIRST_PAGE = new WizardPagePosition("first");
    public static final WizardPagePosition LAST_PAGE = new WizardPagePosition("last");
    public static final WizardPagePosition MIDDLE_PAGE = new WizardPagePosition("middle");
  }
  
  void addPageListener(PageListener l);
  void removePageListener(PageListener l);
  void pageAdded(WizardPagePosition pagePos);
  void pageSkipped();
}
