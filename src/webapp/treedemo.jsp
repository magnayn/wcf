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

<h2>WCF Tree Test</h2>

<form action="treedemo.jsp" method="post" id="form01">
<wcf:scroller/>
<wcf:tree id="tree01"/>
<wcf:render ref="#{tree01}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=tree01&amp;iehack=file.xml">show xml</a>
</body>
</html>
