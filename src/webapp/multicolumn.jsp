<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF Testpage</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
<script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<jsp:useBean id="testbean" class="com.tonbeller.wcf.form.TestBean" scope="session" />
<wcf:form id="multicolumnForm" xmlUri="/WEB-INF/multicolumn.xml" model="#{testbean}"/>


<h2>WCF Test Form</h2>

<form action="multicolumn.jsp" method="post" id="form01">
  <wcf:scroller/>
  <wcf:render ref="#{multicolumnForm}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</form>
<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=formcomp&amp;iehack=file.xml">show xml</a>

</body>
</html>
