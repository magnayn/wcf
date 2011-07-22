<%@ page session="true" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>Token 2</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/wcf.css">
</head>
<body bgcolor=white>

<form action="token3.jsp" method="get" id="form01">
  <wcf:statusline/>
  <h2>Token 2</h2>
  <wcf:token/>
  <input type="submit" name="next" value="next"></input>
</form>

</body>
</html>
