<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <title>Role Test</title>
</head>

<body>

<h2>Role Test</h2>


<form id="roleform">

<wcf:ifRole role="tomcat">
  <h1>Hi Tomcat</h1>
</wcf:ifRole>

<wcf:ifRole role="!tomcat">
  <h1>You are not a Tomcat</h1>
</wcf:ifRole>

</form>

</body>

</html>
