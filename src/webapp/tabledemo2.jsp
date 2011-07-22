<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
<title>WCF Table Test</title>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/table/xtable.css">
  <script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF Table Test 2</h2>

<jsp:useBean id="tmodel2" class="com.tonbeller.wcf.table.TestModel" scope="session" />

WCF Table, no editing, readOnly

<form action="tabledemo2.jsp" method="post" id="form01">

<wcf:table id="table2" model="#{tmodel2}" editable="false"/>
<c:set target="${table2}" property="readOnly" value="true"/>
<wcf:render ref="#{table2}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>


</form>
<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=t1&amp;iehack=file.xml">show xml</a>

</body>
</html>
