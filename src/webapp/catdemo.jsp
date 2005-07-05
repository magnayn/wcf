<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF CatEdit Test</title>
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/catedit/xcatedit.css">
  <script src="wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<jsp:useBean id="catmodel" class="com.tonbeller.wcf.catedit.TestCategoryModel" scope="session" />

<h2>WCF Category Editor Test</h2>

<form action="catdemo.jsp" method="post" id="form01">

<wcf:scroller/>
<wcf:catedit id="catedit01" model="catmodel" validate="true"/>
<wcf:render ref="#{catedit01}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<!-- invisible, since we are not authenticated -->
<jsp:useBean id="cateditTestbean" class="com.tonbeller.wcf.form.TestBean" scope="session" />
<wcf:form role="tomcat" id="cateditFormcomp" xmlUri="/WEB-INF/formdemo.xml" model="#{cateditTestbean}"/>
<wcf:render ref="#{cateditFormcomp}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>
<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=catedit01&amp;iehack=file.xml">show xml</a>
</body>
</html>
