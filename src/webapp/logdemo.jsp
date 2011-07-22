<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
  <title>Logging Administration Form</title>
</head>

<body>

<h2>WCF Logging</h2>

<wcf:logform id="logdemo" xmlUri="/WEB-INF/logdemo.xml" logDir="wcf/logging"/>

<form action="logdemo.jsp" method="post" id="logform">
  <wcf:render ref="logdemo" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</form>

<p><a href="index.jsp">back to index</a>

</body>

</html>
