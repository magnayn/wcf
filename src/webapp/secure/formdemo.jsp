<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF Testpage</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
</head>
<body bgcolor=white>

<jsp:useBean id="testbean" class="com.tonbeller.wcf.form.TestBean" scope="session" />
<wcf:form role="tomcat" id="formcomp" xmlUri="/WEB-INF/formdemo.xml" model="#{testbean}"/>

<h2>WCF Test Form</h2>

<form action="formdemo.jsp" method="post" id="form01">
<wcf:render ref="#{formcomp}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<wcf:catedit role="!tomcat" id="catedit01"/>
<wcf:render ref="#{catedit01}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</form>


</body>
</html>
