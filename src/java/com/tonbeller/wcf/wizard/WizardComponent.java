package com.tonbeller.wcf.wizard;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.ComponentSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.wizard.WizardPage.WizardPagePosition;

/**
 * @author av
 */
public class WizardComponent extends ComponentSupport {
  private List pages = new ArrayList();
  private WizardPage current = null;

  private static Logger logger = Logger.getLogger(WizardComponent.class);

  private PageListener pageListener = new PageListener() {
    public void onNext(RequestContext context) throws Exception {
      setCurrentPageIndex(getCurrentPageIndex() + 1);
    }

    public void onBack(RequestContext context) throws Exception {
      setCurrentPageIndex(getCurrentPageIndex() - 1);
    }

    public void onFinish(RequestContext context) throws Exception {
      // inform the pages that were skipped
      int pageCnt = pages.size();
      int cur = getCurrentPageIndex();
      for(int i=cur+1; i<pageCnt; i++) {
        WizardPage page = (WizardPage) pages.get(i);
        page.pageSkipped();
      }
        
      setCurrentPageIndex(0);
    }

    public void onCancel(RequestContext context) throws Exception {
      setCurrentPageIndex(0);
    }
  };

  public WizardComponent(String id, Component parent) {
    super(id, parent);
  }
  
  public int getPageCount() {
    return pages.size();
  }

  public int getCurrentPageIndex() {
    return pages.indexOf(current);
  }
  
  public void setCurrentPageIndex(int page) {
    if(page>=pages.size())
      throw new IllegalArgumentException("Invalid index " + page);

    current = (WizardPage) pages.get(page);
    adjustVisibility();
  }
  
  public WizardPage getCurrentPage() {
    return current;
  }
  
  public void setCurrentPage(WizardPage page) {
    if (!pages.contains(page))
      throw new IllegalArgumentException("page must be registered with the wizard");
    this.current = page;
    adjustVisibility();
  }

  public void addPage(WizardPage page) {
    pages.add(page);
    page.addPageListener(pageListener);
    int cnt = pages.size();
    for(int i=0; i<cnt; i++) {
      WizardPage wp = (WizardPage) pages.get(i);
      if(cnt==1)
        wp.pageAdded(WizardPagePosition.SINGLE_PAGE);
      else if(i==0)
        wp.pageAdded(WizardPagePosition.FIRST_PAGE);
      else if(i==cnt-1)
        wp.pageAdded(WizardPagePosition.LAST_PAGE);
      else 
        wp.pageAdded(WizardPagePosition.MIDDLE_PAGE);
    }
  }
  
  public void removePage(WizardPage page) {
    page.removePageListener(pageListener);
    pages.remove(page);
  }

  public void initialize(RequestContext context) throws Exception {
    super.initialize(context);
    setCurrentPageIndex(0);
  }

  public Document render(RequestContext context) throws Exception {
    return current.render(context);
  }

  private void adjustVisibility() {
    int pageCnt = pages.size();
    for(int i=0; i<pageCnt; i++) {
      WizardPage p = (WizardPage) pages.get(i);
      p.setVisible(p==current && this.isVisible());
    }
  }
  
  public WizardPage getPage(int idx) {
    return (WizardPage) pages.get(idx);
  }
  
}