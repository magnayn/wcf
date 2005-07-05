<%@ page session="true" contentType="text/html; charset=UTF-8"
   import="com.tonbeller.wcf.tree.*, com.tonbeller.wcf.selection.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>Move Tree Nodes via Cut/Paste</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

<script src="wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>

</head>
<body bgcolor=white>

<h2>Move Tree Nodes via Cut/Paste</h2>

<form action="treedemo3.jsp" method="post" id="form01">

<wcf:scroller/>

<c:if test="${param['init'] != null}">
<%
  TreeModel tm = new TestTreeModel(new int[]{1,2,3,5,2});
  tm = new MutableTreeModelDecorator(tm);
  pageContext.setAttribute("tree03model", tm, PageContext.SESSION_SCOPE);
%>
</c:if>

<wcf:tree id="tree03" model="tree03model"/>

<c:if test="${param['init'] != null}">
  <c:set target="${tree03}" property="cutPasteMode" value="true"/>
</c:if>

<wcf:render ref="#{tree03}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=tree03&amp;iehack=file.xml">show xml</a>
</body>
</html>
