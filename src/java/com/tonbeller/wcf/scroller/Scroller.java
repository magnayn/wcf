package com.tonbeller.wcf.scroller;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Generates hidden input fields that are required to keep scrolling position.
 * Provides static methods to enable scrolling.
 */
public class Scroller {

  private static String SCROLLER_KEY = Scroller.class.getName();
  public static final String FORCE_SCROLLER = "forcescroller";

  public void handleRequest(RequestContext context, JspWriter out) throws IOException {
    // Aktuelle Scrollkoordinaten als versteckte Formparameter setzen
    writeCoordParam(context, out, "wcfXCoord");
    writeCoordParam(context, out, "wcfYCoord");
  }

  private void writeCoordParam(RequestContext context, JspWriter out, String coordName) throws IOException {
    String coordVal = "0";
    if(isScrollerEnabled(context)) {
	    String val = context.getParameter(coordName);
	    if (val != null)
	      coordVal = val;
    }
    out.print("<input type=\"hidden\" name=\""+coordName+"\" value=\""+coordVal+"\"/>");
  }
  
  /**
   * enables/disables scrolling for this request. Typically a request handler should
   * decide whether the current position should be kept or not. A tree node
   * expansion handler would want to keep the position a dialog close button
   * handler not.
   * <p>
   * By default scrolling is disabled
   */
  public static void enableScroller(RequestContext context) {
    if(isScrollerEnabled(context))
      return;
    context.getRequest().setAttribute(SCROLLER_KEY, "true");
  }
  
  public static boolean isScrollerEnabled(RequestContext context) {
    // set by url?
    if(context.getParameter(FORCE_SCROLLER)!=null)
      return true;
    
    // set by request listener?
    return context.getRequest().getAttribute(SCROLLER_KEY)!=null;
  }
}
