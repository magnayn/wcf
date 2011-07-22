<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Table Test</title>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/table/xtable.css">
  <script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF Table Test</h2>

<jsp:useBean id="tmodel" class="com.tonbeller.wcf.table.TestModel" scope="session" />


This page shows a table cooperating with a form.


<form action="tabledemo.jsp" method="post" id="form01">

<!-- optional: keep browser window position when switching between pages -->
<wcf:scroller/>
<wcf:table id="t1" model="#{tmodel}" editable="true"/>
<wcf:render ref="#{t1}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<p>

<%--
<wcf:tablePropertiesForm id="t1properties" closable="false" xmlUri="/WEB-INF/wcf/tableproperties.xml" table="#{t1}"/>
<wcf:render ref="#{t1properties}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
--%>

</form>
<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=t1&amp;iehack=file.xml">show xml</a>

</body>
</html>
