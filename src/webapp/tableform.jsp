<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Tree Test</title>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/table/xtable.css">
  <script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF TableForm Test</h2>
A Form containing a table component.
<p>

<jsp:useBean id="tableformbean" class="com.tonbeller.wcf.table.TestBean" scope="session" />
<wcf:form id="tableform" xmlUri="/WEB-INF/tableform.xml" model="#{tableformbean}"/>

<form action="tableform.jsp" method="post" id="form01">
  <wcf:scroller/>
  <wcf:render ref="#{tableform}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="false"/>
</form>
<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=tableform&amp;iehack=file.xml">show xml</a>

</body>
</html>
