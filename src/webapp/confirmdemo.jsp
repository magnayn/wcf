<%@ page session="true" contentType="text/html; charset=UTF-8"   %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF Testpage</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
</head>
<body bgcolor=white>

<wcf:confirm visible="true"/>

<h2>WCF Confirmation Form</h2>

<form action="confirmdemo.jsp" method="post" id="form01">
<wcf:render ref="#{confirmForm}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=confirm&amp;iehack=file.xml">show xml</a>

</body>
</html>
