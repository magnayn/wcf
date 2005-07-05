<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF Wizard Testpage</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
</head>
<body bgcolor=white>

<!-- bean has no state, so it can be used with more than one form -->
<jsp:useBean id="wizbtnbean" class="com.tonbeller.wcf.test.WizardBtnTestBean" scope="session" />

<h2>WCF Wizard Button Testpage</h2>

<form action="wizard-btn.jsp" method="post" id="form01">

  <h3>Wizard with several pages</h3>

  <wcf:wizard id="wizardMult01">
    <wcf:form id="wizformMult01a" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}"/>
    <wcf:form id="wizformMult01b" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}"/>
    <wcf:form id="wizformMult01c" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}"/>
  </wcf:wizard>
  <wcf:render ref="#{wizardMult01}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

  <h3>Wizard with several pages (finish button only on last page)</h3>

  <wcf:wizard id="wizardMult02">
    <wcf:form id="wizformMult02a" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}" finishButton="false"/>
    <wcf:form id="wizformMult02b" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}" finishButton="false"/>
    <wcf:form id="wizformMult02c" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}" finishButton="false"/>
  </wcf:wizard>
  <wcf:render ref="#{wizardMult02}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

  <h3>"Wizard" with a single page</h3>

  <wcf:wizard id="wizardSingle">
    <wcf:form id="wizformSingle01" xmlUri="/WEB-INF/wizform-btn.xml" model="#{wizbtnbean}"/>
  </wcf:wizard>
  <wcf:render ref="#{wizardSingle}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=wizard01&amp;iehack=file.xml">show xml</a>
</body>
</html>
