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
package com.tonbeller.wcf.component;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.expr.ExprUtils;
import com.tonbeller.wcf.token.RequestToken;
import com.tonbeller.wcf.utils.DomUtils;
import com.tonbeller.wcf.utils.XmlUtils;

/**
 * renders a Component via xsl stylesheet. The following
 * stylesheet parameters are provided automatically (w/o nested parameter tag):
 * <ul>
 * <li><code>renderId</code> - contains the id of this tag. If not set the modelReference is taken
 * <li><code>context</code> - the context path of the application for building URLs
 * </ul>
 * Parameters may occur in xsl (as usual) and in the <code>Document</code> created by the
 * <code>Component</code>
 * <p>
 * Searches for &lt;param name=".."/&gt; elements and creates
 * a corresponding attribute in its parent. For example, if a <code>Component</code>s
 * <code>render()</code> method returns the following <code>Document</code>
 * <pre>
 * &lt;myelem&gt;
 *   &lt;param name="abc" attr="bcd"/&gt;
 * &lt;/myelem&gt;
 * </pre>
 * it will become
 * <pre>
 * &lt;myelem bcd="efg"&gt;
 *   &lt;param name="abc" attr="bcd"/&gt;
 * &lt;/myelem&gt;
 * </pre>
 * where <code>efg</code> is the value of the <code>abc</code> parameter.
 * If the parameter <code>abc</code> does not exsist, the attribute
 * <code>bcd</code> is removed.
 * <p>
 * This may be used to configure the page flow from a jsp, e.g.
 * <pre>
 *   &lt;button label="OK" action="validate" handler="...">
 *     &lt;param name="successPage" attr="forward"/>
 *   &lt;/button>
 * </pre>
 * will create a forward attribute to the button element whose value is supplied by the jsp author.
 *
 * @author av
 */
public class RendererTag extends TagSupport {
  String xslUri;
  boolean xslCache = true;
  String ref;
  Map parameters = new HashMap();

  private static Logger logger = Logger.getLogger(RendererTag.class);

  /**
   * adds a parameter that is valid for this transformation only.
   */
  void addParameter(String name, Object value) {
    parameters.put(name, value);
  }

  /**
   * removes a parameter that is defined globally for this transformation
   * @param name
   */
  public void removeParameter(String name) {
    parameters.remove(name);
  }

  public int doStartTag() throws JspException {
    parameters.putAll(RendererParameters.getParameterMap((HttpServletRequest) pageContext.getRequest()));
    return EVAL_BODY_INCLUDE;
  }

  /**
   * renders the component
   */
  public int doEndTag() throws JspException {
    logger.info("enter " + ref);

    try {
      RequestContext context = RequestContext.instance();
      Object x = context.getModelReference(getRef());
      if (x == null)
        throw new JspException("component \"" + getRef() + "\" not found");
      if(!(x instanceof Renderable))
        throw new JspException("component \"" + getRef() + "\" is not Renderable: " + x.getClass());
      Renderable comp = (Renderable) x;

      if (comp instanceof Visible && !((Visible) comp).isVisible())
        return EVAL_PAGE;

      if (comp instanceof RoleExprHolder) {
        String roleExpr = ((RoleExprHolder) comp).getRoleExpr();
        if (!context.isUserInRole(roleExpr))
          return EVAL_PAGE;
      }

      // add "context" and "renderId" to parameter map
      createPredefinedParameters(context);

      Transformer transformer = XmlUtils.getTransformer(pageContext.getSession(), xslUri, xslCache);
      setXslParameters(context, transformer);

      Document document = comp.render(context);

      //      PrintWriter pw = new PrintWriter(System.out);
      //      XmlUtils.print(document, pw);
      //      pw.flush();

      setXmlParameters(document);

      // we can not render to the output stream directly, because this gives an
      // "illecal flush" exception in JSF EA2
      DOMSource source = new DOMSource(document);
      StringWriter sw = new StringWriter();
      StreamResult result = new StreamResult(sw);

      transformer.transform(source, result);
      sw.flush();
      pageContext.getOut().write(sw.toString());

      parameters.clear();
      logger.info("leave " + ref);
      return EVAL_PAGE;
    } catch (Exception e) {
      logger.error("trouble rendering " + getRef(), e);
      throw new JspException(e.toString(), e);
    }
  }

  /**
   * sets the parameters to the xml document.
   * Searches for &lt;param name=".."/&gt; elements and creates
   * a corresponding attribute in its parent. For example,
   * <pre>
   * &lt;myelem&gt;
   *   &lt;param name="abc" attr="bcd"/&gt;
   * &lt;/myelem&gt;
   * </pre>
   * will become
   * <pre>
   * &lt;myelem bcd="efg"&gt;
   *   &lt;param name="abc" attr="bcd"/&gt;
   * &lt;/myelem&gt;
   * </pre>
   * where <code>efg</code> is the value of the <code>abc</code> parameter.
   * If the parameter <code>abc</code> does not exsist, the attribute
   * <code>bcd</code> is removed.
   */
  protected void setXmlParameters(Document document) /*throws JaxenException*/ {
    /* eb 16.05.03 - Jaxen ist teuer
        DOMXPath xp = new DOMXPath("//param");
        List list = xp.selectNodes(document);
        for (Iterator it = list.iterator(); it.hasNext();) {
          Element param = (Element) it.next();
          String paramName = param.getAttribute("name");
          String attrName = param.getAttribute("attr");
          String value = (String)parameters.get(paramName);
          Element parent = (Element) param.getParentNode();
          if (value == null || value.length() == 0)
            parent.removeAttribute(attrName);
          else
            parent.setAttribute(attrName, value);
        }
    */
    // tables etc may be *very* large, so we search for
    // parameters only in <xform ..> documents.
    Element root = document.getDocumentElement();
    if (root != null && "xform".equals(root.getNodeName()))
      setXmlParameters(document.getChildNodes());
  }

  protected void setXmlParameters(NodeList list) {
    int len = list.getLength();
    for (int i = 0; i < len; i++) {
      Node n = list.item(i);
      if (n.getNodeType() != Node.ELEMENT_NODE)
        continue;
      Element x = (Element) list.item(i);

      if ("param".equals(x.getNodeName())) {
        String paramName = x.getAttribute("name");
        String attrName = x.getAttribute("attr");
        String value = (String) parameters.get(paramName);
        Element parent = (Element) x.getParentNode();
        if (value == null || value.length() == 0)
          DomUtils.removeAttribute(parent, attrName);
        else
          parent.setAttribute(attrName, value);
      } else {
        setXmlParameters(x.getChildNodes());
      }
    }
  }

  /**
   * sets the parameters to the transformer
   */
  void setXslParameters(RequestContext context, Transformer transformer) {
    for (Iterator it = parameters.keySet().iterator(); it.hasNext();) {
      String name = (String) it.next();
      Object value = parameters.get(name);
      transformer.setParameter(name, value);
    }
  }

  /** add "context" and "renderId" to parameter map */
  private void createPredefinedParameters(RequestContext context) throws MalformedURLException {
    String renderId = this.getId();
    if (renderId == null || renderId.length() == 0)
      renderId = ExprUtils.getBeanName(getRef());
    parameters.put("renderId", renderId);
    parameters.put("context", context.getRequest().getContextPath());
    // Some FOP-PDF versions require a complete URL, not a path
    parameters.put("contextUrl", createContextURLValue(context));

    // if there, add token to control page flow
    if (context.getSession() != null) { 
	    RequestToken tok = RequestToken.instance(context.getSession());
	    if (tok != null) {
	    	parameters.put("token", tok.getHttpParameterName() + "=" + tok.getToken());
	    }
    }
  }

  /** generates the context URL stylesheet parameter */
  private String createContextURLValue(RequestContext context) throws MalformedURLException {

    if (context.getRequest() == null || context.getRequest().getRequestURL() == null) {
        return "UNDEFINED";
    }
    
    URL url = new URL((context.getRequest()).getRequestURL().toString());

    StringBuffer c = new StringBuffer();
    c.append(url.getProtocol());
    c.append("://");
    c.append(url.getHost());
    if (url.getPort() != 80) {
      c.append(":");
      c.append(url.getPort());
    }
    c.append(context.getRequest().getContextPath());

    return c.toString();
  }

  /**
   * Returns the ref.
   * @return String
   */
  public String getRef() {
    return ref;
  }

  /**
   * Returns the xslCache.
   * @return boolean
   */
  public boolean isXslCache() {
    return xslCache;
  }

  /**
   * Returns the xslUri.
   * @return String
   */
  public String getXslUri() {
    return xslUri;
  }

  /**
   * Sets the ref.
   * @param ref The ref to set
   */
  public void setRef(String ref) {
    this.ref = ref;
  }

  /**
   * Sets the xslCache.
   * @param xslCache The xslCache to set
   */
  public void setXslCache(boolean xslCache) {
    this.xslCache = xslCache;
  }

  /**
   * Sets the xslUri.
   * @param xslUri The xslUri to set
   */
  public void setXslUri(String xslUri) {
    this.xslUri = xslUri;
  }

}
