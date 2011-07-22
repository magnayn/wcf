<%@ page session="true" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Tree Test</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/tree/xtree.css">
<script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF Tree Test</h2>

<c:if test="${!(empty param.selmode)}">
  change mode.
  <c:set target="${tree01.selectionModel}" property="mode" value="${param.selmode}" />
</c:if>

<form action="treedemo.jsp" method="post" id="form01">
<wcf:scroller/>
<wcf:tree id="tree01"/>
<wcf:render ref="#{tree01}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<form action="treedemo.jsp" method="get" id="form02">
  <select name="selmode">
    <option value="0">NO_SELECTION</option>
    <option value="1">SINGLE_SELECTION</option>
    <option value="2">MULTIPLE_SELECTION</option>
    <option value="3">SINGLE_SELECTION_HREF</option>
    <option value="4">MULTIPLE_SELECTION_BUTTON</option>
    <option value="5">MULTIPLE_SELECTION_HREF</option>
    <option value="6">SINGLE_SELECTION_BUTTON</option>
  </select>
  <p />
  <input type="submit">
</form>

<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=tree01&amp;iehack=file.xml">show xml</a>
</body>
</html>
