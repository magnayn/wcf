<%@ page session="true" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Tree Test</title>
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">
  <script src="wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF TreeForm Test</h2>
A Form containing a tree component.
<p>

<jsp:useBean id="treeformbean" class="com.tonbeller.wcf.tree.TestBean" scope="session" />
<wcf:form id="treeform" xmlUri="/WEB-INF/treeform.xml" model="${treeformbean}"/>

<form action="treeform.jsp" method="post" id="form01">
  <wcf:scroller/>
  <wcf:render ref="${treeform}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="false"/>
</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=treeform&amp;iehack=file.xml">show xml</a>
</body>
</html>
