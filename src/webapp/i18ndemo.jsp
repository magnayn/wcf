<%@ page
  session="true"
  contentType="text/html; charset=UTF-8"
  import="com.tonbeller.wcf.controller.*,
    java.util.*,
    javax.servlet.http.*,
    com.tonbeller.tbutils.res.*"
%>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%
  RequestContext ctx = RequestContext.instance();
  String lang = ctx.getParameter("lang");
  if (lang != null) {
    Locale loc = new Locale(lang, lang.toUpperCase());
    ctx.setLocale(loc);
  }
  Resources res = ctx.getResources(com.tonbeller.wcf.test.TestBean.class);
%>

<html>
<head>
<title>WCF i18n Testpage</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
</head>
<body bgcolor=white>

<jsp:useBean id="i18nbean" class="com.tonbeller.wcf.form.TestBean" scope="session" />
<wcf:form id="i18nform" bundle="com.tonbeller.wcf.test.resources" xmlUri="/WEB-INF/i18nform.xml"  model="#{i18nbean}"/>

<h2>i18n Test</h2>

<form action="i18ndemo.jsp" method="post" id="form01">
<table>
  <tr>
    <td>fmt:message</td>
    <td><fmt:message key="message.key"/></td>
  </tr>
  <tr>
    <td>resources</td>
    <td><%= res.getString("test.message") %></td>
  </tr>
  <tr>
</table>
<wcf:render ref="#{i18nform}" xslUri="/WEB-INF/wcf/wcf.xsl"/>
</form>
<p><a href="index.jsp">back to index</a>

</body>
</html>
