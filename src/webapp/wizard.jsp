<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
<title>WCF Wizard Testpage</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
</head>
<body bgcolor=white>

<jsp:useBean id="wizbean" class="com.tonbeller.wcf.test.WizardTestBean" scope="session" />

<h2>WCF Wizard Testpage</h2>

<form action="wizard.jsp" method="post" id="form01">
  <wcf:wizard id="wizard01">
    <wcf:form id="wizform01" xmlUri="/WEB-INF/wizform01.xml" model="#{wizbean}"/>
    <wcf:form id="wizform02" xmlUri="/WEB-INF/wizform02.xml" model="#{wizbean}"/>
    <wcf:form id="wizform03" xmlUri="/WEB-INF/wizform03.xml" model="#{wizbean}"/>
  </wcf:wizard>
  <wcf:render ref="#{wizard01}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

  Step
  <c:if test="${wizform01.visible}">
    1
  </c:if>
  <c:if test="${wizform02.visible}">
    2
  </c:if>
  <c:if test="${wizform03.visible}">
    3
  </c:if>
</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=wizard01&amp;iehack=file.xml">show xml</a>
</body>
</html>
