<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF Testpage</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
</head>
<body bgcolor=white>

<jsp:useBean id="testbean" class="com.tonbeller.wcf.form.TestBean" scope="session" />
<wcf:form id="uploadcomp" xmlUri="/WEB-INF/uploaddemo.xml" model="#{testbean}"/>


<h2>WCF Test File Upload</h2>

<form action="uploaddemo.jsp" method="post" id="form01" enctype="multipart/form-data">
  <wcf:render ref="#{uploadcomp}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</form>

<pre id='uploadInfo'>
<c:out value="${testbean.uploadInfo}"/>
</pre>

<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=uploadcomp&amp;iehack=file.xml">show xml</a>

</body>
</html>
