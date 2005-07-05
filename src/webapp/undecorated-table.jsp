<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>Undecorated Table</title>
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <script src="wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>Undecorated Table</h2>

<jsp:useBean id="utmodel" class="com.tonbeller.wcf.table.TestModel" scope="session">
  <jsp:setProperty name="utmodel" property="title" value="<%= null %>"/>
</jsp:useBean>


<p>This page shows a table without title and column headers</p>


<form action="undecorated-table.jsp" method="post" id="form01">

<wcf:table id="ut1" model="#{utmodel}" editable="false" closable="false" colHeaders="false"/>
<wcf:render ref="#{ut1}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<p>

</form>
<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=ut1&amp;iehack=file.xml">show xml</a>

</body>
</html>
