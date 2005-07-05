/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.table;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.format.FormatException;
import com.tonbeller.wcf.format.FormatHandler;
import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * a renderer that renders TableRow values using the Formatter instance for this request.
 * @see com.tonbeller.xoplon.format.Formatter
 */
public class DefaultCellRenderer implements CellRenderer {
  Alignment alignment = Alignment.GUESS;
  String format;
  String type;
  private static final String NBSP = "\u00a0";

  public static class Alignment {
    
    /**
     * alignment depends on content. Numbers are right-aligned, 
     * String left-aligned
     */
    public static final Alignment GUESS = new Alignment("");

    /**
     * Left alignment
     */
    public static final Alignment LEFT = new Alignment("left");
    
    /**
     * Right alignment
     */
    public static final Alignment RIGHT = new Alignment("right");

    private String attributeValue;
    
    private Alignment(String attributeValue) {
      this.attributeValue = attributeValue;
    }
    
    public String getAttributeValue() {
      return attributeValue;
    }
  }
  
  public DefaultCellRenderer() {
  }

  public void render(RequestContext context, Element td, Object value) throws FormatException {
    Element elem = td;
    boolean needSpace = true;

    if (value instanceof DefaultCell) {
      DefaultCell hc = (DefaultCell)value;
      value = hc.getValue();
      if (hc.getURL() != null) {
        elem = DomUtils.appendElement(elem, "a");
        elem.setAttribute("href", hc.getURL());
        if (hc.getTarget() != null)
          elem.setAttribute("target", hc.getTarget());
        if (hc.getOnClick() != null)
          elem.setAttribute("onClick", hc.getOnClick());
      }

      if (hc.getImage() != null) {
        Element img = DomUtils.appendElement(elem, "img");
        img.setAttribute("border", "0");
        img.setAttribute("src", hc.getImage());
        DomUtils.appendText(elem, " ");
        needSpace = false;
      }
    }

    if (value != null) {
      Formatter fmt = context.getFormatter();
      FormatHandler fh = null;
      if (type != null)
        fh = fmt.getHandler(type);
      if (fh == null)
        fh = fmt.guessHandler(value);
      String s;
      if (fh != null)
        s = fh.format(value, format);
      else if (value != null)
        s = value.toString();
      else 
        s = null;
      if (s == null || s.length() == 0 || s.trim().length()==0)
        s = NBSP;
      DomUtils.appendText(elem, s);
      if(alignment == Alignment.GUESS) {
        if (value instanceof Number)
          td.setAttribute("align", "right");
        else
          td.setAttribute("align", "left");
      } 
      else 
        td.setAttribute("align", alignment.getAttributeValue());
      
    }

    else if (needSpace)
      DomUtils.setText(elem, NBSP);
  }

  /**
   * format string for formatter
   * @see com.tonbeller.xoplon.format.FormatHandler
   */
  public void setFormat(String newFormat) {
    format = newFormat;
  }
  /**
   * format string for formatter
   * @see com.tonbeller.xoplon.format.FormatHandler
   */
  public String getFormat() {
    return format;
  }
  /**
   * data type for formatter
   * @see com.tonbeller.xoplon.format.FormatHandler
   */
  public void setType(String newType) {
    type = newType;
  }
  /**
   * data type for formatter
   * @see com.tonbeller.xoplon.format.FormatHandler
   */
  public String getType() {
    return type;
  }

  /**
   * set column alignment 
   */
  public void setAlignment(Alignment alignment) {
    this.alignment = alignment;
  }
}