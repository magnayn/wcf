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
package com.tonbeller.wcf.param;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.Controller;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.expr.ExprUtils;
import com.tonbeller.wcf.token.RequestToken;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * produces an &lt;a href="..." &gt; hyperlink.
 * Stores a list of SessionParam instances. If the user clicks on
 * the hyperlink, all these parameters will be added to the SessionParamPool.
 *
 * @author av
 */
public class ParamLinkTag extends TagSupport {
  String page;

  String displayName;
  String displayValue;
  String paramName;
  String sqlValue;
  String mdxValue;
  String textValue;
  String target; // equivalent to the HTML target

  // shall we generate a token to disallow browser-back button?
  boolean token;

  /**
   * null if no anchors are rendered, i.e. only text is displayed
   * @see ParamLinkGroupTag#isRenderActions()
   */
  ClickHandler clickHandler;

  /**
   * Actions may be added to the link. They are executed
   * when the user clicks on the link.
   *
   * @author av
   */
  public interface Action {
    void execute(RequestContext context) throws Exception;
  }

  /**
   * invokes a Method with the signature
   * <p>
   * methodName(RequestContext context) throws Exception;
   *
   * @author av
   */
  public static class InvokeMethodAction implements Action {
    private String methodName;
    private String targetExpr;

    InvokeMethodAction(String targetExpr, String methodName) {
      this.targetExpr = targetExpr;
      this.methodName = methodName;
    }

    public void execute(RequestContext context) throws Exception {
      Object target = context.getModelReference(targetExpr);
      Class c = target.getClass();
      Method m = c.getMethod(methodName, new Class[] { RequestContext.class});
      m.invoke(target, new Object[] { context});
    }
  }

  private static Logger logger = Logger.getLogger(ParamLinkTag.class);

  static class ClickHandler implements RequestListener {
    private List params = new ArrayList();
    private List actions = new ArrayList();
    private String page;

    ClickHandler(String page) {
      this.page = page;
    }

    public void addParam(SessionParam param) {
      params.add(param);
    }

    public void addAction(Action action) {
      actions.add(action);
    }

    public void request(RequestContext context) throws Exception {
      // set parameters
      SessionParamPool pool = SessionParamPool.instance(context.getSession());
      for (Iterator it = params.iterator(); it.hasNext();) {
        SessionParam p = (SessionParam) it.next();
        if (logger.isInfoEnabled()) {
          logger.info("setting parameter: " + p);
        }
        pool.setParam(p);
      }
      // execute actions
      for (Iterator it = actions.iterator(); it.hasNext();) {
        Action a = (Action) it.next();
        a.execute(context);
      }
      // forward to next page
      if (page != null)
        Controller.instance(context.getSession()).setNextView(page);
    }
  }

  ParamLinkGroupTag groupTag;

  public int doStartTag() throws JspException {
    try {
      clickHandler = null;
      groupTag = (ParamLinkGroupTag) findAncestorWithClass(this, ParamLinkGroupTag.class);
      if (groupTag == null)
        throw new JspException("ParamLinkTag must be used inside a ParamLinkGroupTag");
      // hide hyperlinks in excel/PDF modes
      if (!groupTag.isRenderActions())
        return EVAL_BODY_INCLUDE;

      String id = DomUtils.randomId();
      clickHandler = new ClickHandler(evalString(page));
      groupTag.getDispatcher().addRequestListener(groupTag.getId(), id, clickHandler);

      Resources res = Resources.instance(ParamLinkTag.class);
      String htmlTarget = "";
      if (getTarget() != null) {
        htmlTarget = " " + res.getString("paramLinkTag.targetTag", getTarget());
      }

      String href = "?" + groupTag.getId() + "=" + id;
      if (token) {
        RequestToken rt = RequestToken.instance(pageContext.getSession());
        href = rt.appendHttpParameter(href);
      }

      Object[] args = { href, htmlTarget};
      String s = res.getString("paramLinkTag.startTag", args);
      pageContext.getOut().print(s);

      if (paramName != null)
        addParam(paramName, displayName, displayValue, sqlValue, mdxValue, textValue);

    } catch (IOException e) {
      logger.error(null, e);
    }

    return EVAL_BODY_INCLUDE;
  }

  /**
   * creates a SessionParam from the arg list and adds it to the list of parameters
   * @param paramName
   * @param displayName
   * @param displayValue
   * @param sqlValue
   * @param mdxValue
   * @param textValue
   */
  public void addParam(String paramName, String displayName, String displayValue, String sqlValue,
      String mdxValue, String textValue) {

    if (paramName == null)
      throw new IllegalArgumentException("parameter name is null");

    SessionParam p = new SessionParam();
    p.setName(paramName);
    p.setDisplayName(evalString(displayName));
    p.setDisplayValue(evalString(displayValue));
    p.setTextValue(evalString(textValue));
    p.setMdxValue(mdxValue);
    p.setSqlValue(eval(sqlValue));
    addParam(p);
  }

  /**
   * @param p
   */
  public void addParam(SessionParam p) {
    if (clickHandler != null)
      clickHandler.addParam(p);
  }

  public void addAction(Action a) {
    if (clickHandler != null)
      clickHandler.addAction(a);
  }

  public int doEndTag() throws JspException {
    if (!groupTag.isRenderActions())
      return super.doEndTag();
    try {
      Resources res = Resources.instance(ParamLinkTag.class);
      String s = res.getString("paramLinkTag.endTag");
      pageContext.getOut().print(s);
    } catch (IOException e) {
      logger.error(null, e);
    }
    clickHandler = null;
    return super.doEndTag();
  }

  private Object eval(String expr) {
    if (expr == null)
      return null;
    if (ExprUtils.isExpression(expr))
      return ExprUtils.getModelReference(pageContext, expr);
    return expr;
  }

  private String evalString(String expr) {
    if (expr == null)
      return null;
    if (ExprUtils.isExpression(expr))
      return String.valueOf(ExprUtils.getModelReference(pageContext, expr));
    return expr;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }

  public void setMdxValue(String mdxValue) {
    this.mdxValue = mdxValue;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public void setSqlValue(String sqlValue) {
    this.sqlValue = sqlValue;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getTextValue() {
    return textValue;
  }

  public void setTextValue(String textValue) {
    this.textValue = textValue;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getTarget() {
    return target;
  }

  public void setToken(boolean token) {
    this.token = token;
  }

}